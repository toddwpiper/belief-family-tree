package au.gov.ipaustralia.extract.web;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import au.gov.ipaustralia.extract.gsa.trademarks.ATMOSSConsistencyCheckService;
import au.gov.ipaustralia.extract.service.external.ChangeSetService;
import au.gov.ipaustralia.extract.support.data.entity.TmExtract;
import au.gov.ipaustralia.extract.support.data.repo.gsa.ExtractRepository;
import au.gov.ipaustralia.extract.support.enums.BinaryTransferType;
import au.gov.ipaustralia.extract.support.enums.OPIStatus;
import au.gov.ipaustralia.extract.web.model.DetailCountsView;
import au.gov.ipaustralia.extract.web.model.IndexCountsView;
import au.gov.ipaustralia.extract.web.model.SearchStatistic;
import au.gov.ipaustralia.extract.web.model.SearchStatisticGroup;
import au.gov.ipaustralia.extract.web.model.TimesView;

/**
 * Please take a look at the web.xml where JAX-RS is enabled.
 * 
 * Full URI is something like:
 * http://localhost:8080/ipd-extract-rs/rest/verify/count
 * 
 * @author bimlij
 * 
 */
@RestController
@Transactional
@RequestMapping(value = "/ccheckPublic", produces = MediaType.APPLICATION_JSON_VALUE)
public class PublicConsistencyCheckController extends AbstractConsistencyCheckController {

	private static final Logger LOG = LoggerFactory.getLogger(PublicConsistencyCheckController.class);
	private static final String CCHECK_DIFFERENCES_FIX_DESCRIPTION = "ccheck-differences";

	@Autowired
	private ATMOSSConsistencyCheckService checkService;

	@Autowired
	private ChangeSetService changeSetService;

	@Autowired
	private ExtractRepository extractRepository;

	@Value("${ccheck.publicSearch.url}")
	private String publicSearchUrl;

	@Value("${ccheck.publicSearch.name}")
	private String publicSearchName;

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> retryDifferent() {
		changeSetService.processPublicDifferencesChangeSet(CCHECK_DIFFERENCES_FIX_DESCRIPTION, new Date(), OPIStatus.OPI,
				BinaryTransferType.NONE);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	@RequestMapping(value = "/removeOutOfSync", method = RequestMethod.POST)
	public ResponseEntity<?> removeOutOfSync() {
		extractRepository.deleteRecordsThatHaveBeenRemovedFromSource();
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@RequestMapping(value = "/times", method = RequestMethod.GET)
	public List<TimesView> getTimes() throws SQLException, IOException {
		
		LOG.info("Started processing request [/ccheckPublic/times]");
		
		Object[] result = checkService.getLastChangeTimes();

		List<TimesView> times = new ArrayList<>();
		times.add(new TimesView("Trade Mark", (result[0] != null ? String.valueOf(result[0]) : "Not Records Found")));
		times.add(new TimesView("Attorney", (result[0] != null ? String.valueOf(result[1]) : "Not Records Found")));
		times.add(new TimesView("Person", (result[0] != null ? String.valueOf(result[2]) : "Not Records Found")));
		
		LOG.info("Finished processing request [/times]");

		return times;
	}

	@RequestMapping(value = "/counts", method = RequestMethod.GET)
	public List<IndexCountsView> verifyCounts() throws SQLException, IOException {
		
		LOG.info("Started processing request [/ccheckPublic/counts]");
		
		LOG.info("Getting counts from IPA");
		
		
		long countHeadstarts = checkService.countHeadstartRequests();
		LOG.info("countHeadstarts [{}]",countHeadstarts);
		
		long prohibitedMarks = checkService.countProhibitedMarks();
		LOG.info("prohibitedMarks [{}]",prohibitedMarks);
		
		long countExpectedTradeMarks = checkService.countTradeMarks() + prohibitedMarks;
		LOG.info("countExpectedTradeMarks [{}]",countExpectedTradeMarks);

		List<IndexCountsView> counts = new ArrayList<>();
		IndexCountsView sourceCounts = new IndexCountsView("IPA",
				new SearchStatisticGroup(new SearchStatistic(countExpectedTradeMarks), null,
						new SearchStatistic(countHeadstarts), new SearchStatistic(prohibitedMarks)));
		counts.add(sourceCounts);

		LOG.info("Getting counts from Public Search URL");
		// addFrom(counts, publicSearchUrl);
		IndexCountsView publicSearch = addFrom(counts, publicSearchUrl);

		if (publicSearch != null) {
			counts.add(publicSearch);
			addDiff(counts, sourceCounts, publicSearch);
		}
		
		LOG.info("Finished processing request [/ccheckPublic/counts]");

		return counts;
	}

	@RequestMapping(value = "/detailCounts", method = RequestMethod.GET)
	public List<DetailCountsView> verifyDetailCounts() throws SQLException, IOException {
		
		LOG.info("Started processing request [/ccheckPublic/detailCounts]");
		
		LOG.info("Getting counts from Activity Tables");
		
		LOG.info("Getting same counts");
		long countSame = extractRepository.countPublicSame();
		
		LOG.info("Getting public different counts");
		long countDifferent = extractRepository.countPublicDifferent();
		
		LOG.info("Getting public different not completed counts");
		long countDifferentNotInCompleted = extractRepository.countPublicDifferentNotInCompleted();
		
		LOG.info("Getting public in IPA but not Target counts");
		long countInIPANotInTarget = extractRepository.countPublicInIPANotInTarget();
		
		LOG.info("Getting public in Target but not IPA counts");
		long countInTargetNotInIPA = extractRepository.countPublicInTargetNotInIPA();

		List<DetailCountsView> counts = new ArrayList<>();
		counts.add(new DetailCountsView(publicSearchName, countSame, countDifferent, countDifferentNotInCompleted,
				countInIPANotInTarget, countInTargetNotInIPA));
		
		LOG.info("Finished processing request [/ccheckPublic/detailCounts]");

		return counts;
	}

	@RequestMapping(value = "/detailDiff", method = RequestMethod.GET)
	public @ResponseBody PagedResources<Resource<TmExtract>> getListOfExtracts(Pageable pageable,
			PagedResourcesAssembler<TmExtract> assembler) {
		
		LOG.info("Started processing request [/ccheckPublic/detailDiff]");
		
		LOG.info("GET list of delta extracts");
		Page<TmExtract> changeSets = extractRepository.findByAllPublicDifferences(pageable);
		
		LOG.info("Finished processing request [/ccheckPublic/detailDiff]");
		return assembler.toResource(changeSets);
	}

	

	/*
	 * @GET
	 * 
	 * @Path("/detail")
	 * 
	 * @Produces({ "application/json" }) public Response verifyDetail() { try {
	 * //get the list from ATMOSS Set<SearchMeta> sourceDetails =
	 * checkService.getIndexedRecords().stream() .map(tmNumber -> new
	 * SearchMeta(tmNumber, "TMV-13", "2016-03-24+11:00"))
	 * .collect(Collectors.toSet());
	 * 
	 * //get the list from search (which search?) Set<SearchMeta> targetDetails
	 * = getSearchDetails();
	 * 
	 * int numberExactMatches = filterOutExactMatches(sourceDetails,
	 * targetDetails); Set<SearchMeta> sourceNotInTarget =
	 * collectANotInB(sourceDetails, targetDetails); Set<SearchMeta>
	 * targetNotInSource = collectANotInB(targetDetails, sourceDetails);
	 * Set<SearchMetaPair> remainingDiff = mergeRemaining(sourceDetails,
	 * targetDetails);
	 * 
	 * JSONObject obj = convertToJson(numberExactMatches, sourceNotInTarget,
	 * targetNotInSource, remainingDiff);
	 * 
	 * return ok(obj.toString()); } catch (JSONException | SQLException |
	 * IOException e) { return failed(e); } }
	 */

	/*
	 * @GET
	 * 
	 * @Path("/detail2")
	 * 
	 * @Produces({ "application/json" }) public Response verifyDetailTMark() {
	 * try { LOGGER.info("Retrieving from source"); //get the list from ATMOSS
	 * Set<SearchMeta> sourceDetails = checkService.getIndexedRecords().stream()
	 * .map(tmNumber -> new SearchMeta(tmNumber, null, null))
	 * .collect(Collectors.toSet());
	 * 
	 * LOGGER.info("Retrieving from target"); //get the list from search (which
	 * search?) Set<SearchMeta> targetDetails = getSearchDetailsTmark();
	 * 
	 * LOGGER.info("Comparing"); int numberExactMatches =
	 * filterOutExactMatches(sourceDetails, targetDetails); Set<SearchMeta>
	 * sourceNotInTarget = collectANotInB(sourceDetails, targetDetails);
	 * Set<SearchMeta> targetNotInSource = collectANotInB(targetDetails,
	 * sourceDetails); Set<SearchMetaPair> remainingDiff =
	 * mergeRemaining(sourceDetails, targetDetails);
	 * 
	 * JSONObject obj = convertToJson(numberExactMatches, sourceNotInTarget,
	 * targetNotInSource, remainingDiff);
	 * 
	 * return ok(obj.toString()); } catch (JSONException | SQLException |
	 * IOException e) { return failed(e); } }
	 */

	/**
	 * Remove items from both sets that are present in both sets (i.e. the
	 * intersection)
	 * 
	 * Exact match being the equals method of SearchMeta (trade mark number,
	 * change id and change date)
	 * 
	 * @param sourceDetails
	 * @param targetDetails
	 * @return
	 */
	/*
	 * private int filterOutExactMatches(Set<SearchMeta> sourceDetails,
	 * Set<SearchMeta> targetDetails) { Set<SearchMeta> intersection = new
	 * HashSet<>(sourceDetails); intersection.retainAll(targetDetails);
	 * 
	 * sourceDetails.removeAll(intersection);
	 * targetDetails.removeAll(intersection);
	 * 
	 * return intersection.size(); }
	 * 
	 *//**
		 * Return a list comprising those items that are in a but not in b. Also
		 * removes these found items from a.
		 * 
		 * This works on ID only.
		 * 
		 * @param a
		 * @param b
		 * @return
		 *//*
		 * private Set<SearchMeta> collectANotInB(Set<SearchMeta> a,
		 * Set<SearchMeta> b) {
		 * 
		 * Map<String, SearchMeta> searchableB = b.stream()
		 * .collect(Collectors.toMap(SearchMeta::getIpId, s -> s));
		 * 
		 * Set<SearchMeta> inANotInB = a.stream() .filter(aEntry ->
		 * !searchableB.containsKey(aEntry.getIpId()))
		 * .collect(Collectors.toSet());
		 * 
		 * a.removeAll(inANotInB);
		 * 
		 * return inANotInB; }
		 * 
		 * private Set<SearchMetaPair> mergeRemaining(Set<SearchMeta>
		 * sourceDetails, Set<SearchMeta> targetDetails) {
		 * 
		 * Map<String, SearchMetaPair> merged = sourceDetails.stream()
		 * .collect(Collectors.toMap(SearchMeta::getIpId, s -> new
		 * SearchMetaPair(s)));
		 * 
		 * targetDetails.stream().forEach(targetMeta -> { SearchMetaPair found =
		 * merged.get(targetMeta.getIpId()); if (found != null) {
		 * found.setTarget(targetMeta); } else { //shouldn't happen LOG.warn(
		 * "Found an element in the target, that is not in the source. These should have been filtered out already: {}"
		 * , targetMeta); } });
		 * 
		 * return merged.entrySet().stream().map(entry->
		 * entry.getValue()).collect(Collectors.toSet()); }
		 * 
		 * 
		 * private JSONObject convertToJson(int numberExactMatches,
		 * Set<SearchMeta> sourceNotInTarget, Set<SearchMeta> targetNotInSource,
		 * Set<SearchMetaPair> remainingDiff) throws JSONException {
		 * 
		 * JSONObject obj = new JSONObject();
		 * 
		 * obj.put("numberExactMatches", numberExactMatches);
		 * 
		 * obj.put("numberSourceNotInTarget", sourceNotInTarget.size());
		 * 
		 * for (SearchMeta sm : sourceNotInTarget) {
		 * obj.accumulate("sourceNotInTarget", convertSearchMeta(sm)); }
		 * 
		 * obj.put("numberTargetNotInSource", targetNotInSource.size());
		 * 
		 * for (SearchMeta sm : targetNotInSource) {
		 * obj.accumulate("targetNotInSource", convertSearchMeta(sm)); }
		 * 
		 * obj.put("numberRemainingDiff", remainingDiff.size());
		 * 
		 * for (SearchMetaPair smp : remainingDiff) {
		 * obj.accumulate("remainingDiff", convertSearchMetaPair(smp)); }
		 * 
		 * return obj; }
		 * 
		 * private JSONObject convertSearchMetaPair(SearchMetaPair smp) throws
		 * JSONException { JSONObject obj = new JSONObject(); obj.put("source",
		 * convertSearchMeta(smp.getSource())); obj.put("target",
		 * convertSearchMeta(smp.getTarget())); return obj; }
		 * 
		 * private JSONObject convertSearchMeta(SearchMeta sm) throws
		 * JSONException { JSONObject obj = new JSONObject(); obj.put("ipId",
		 * sm.getIpId()); if (sm.getChangeId() != null) { obj.put("changeId",
		 * sm.getChangeId()); } if (sm.getChangeDate() != null) {
		 * obj.put("changeDate", sm.getChangeDate()); } return obj; }
		 * 
		 * // TODO obviously change this to call the real search service private
		 * String getSearchCounts() throws JSONException { return
		 * getSearchCounts("fetch", 2500, 1500); }
		 * 
		 * private Set<SearchMeta> getSearchDetails() throws JSONException,
		 * IOException {
		 * 
		 * StringBuffer buf = new StringBuffer(); try (InputStream is =
		 * this.getClass().getResourceAsStream("/consistency-check-example.json"
		 * ); InputStreamReader inputStreamReader = new InputStreamReader(is);
		 * BufferedReader reader = new BufferedReader(inputStreamReader);) {
		 * 
		 * for (String line = reader.readLine(); line != null; line =
		 * reader.readLine()) { buf.append(line); }
		 * 
		 * JSONArray obj = new JSONArray(buf.toString()); Set<SearchMeta>
		 * targetDetails = new HashSet<>(); for (int i = 0; i < obj.length();
		 * i++) { JSONObject meta = obj.getJSONObject(i); targetDetails.add(new
		 * SearchMeta(meta.getString("ApplicationNumberText"),
		 * meta.getString("ChangeIdentifier"), meta.getString("ChangeDate"))); }
		 * 
		 * return targetDetails; } }
		 * 
		 * private Set<SearchMeta> getSearchDetailsTmark() throws JSONException,
		 * IOException { StringBuffer buf = new StringBuffer(); try (InputStream
		 * is = this.getClass().getResourceAsStream("/Unique TM Numbers.txt");
		 * InputStreamReader inputStreamReader = new InputStreamReader(is);
		 * BufferedReader reader = new BufferedReader(inputStreamReader);) {
		 * 
		 * for (String line = reader.readLine(); line != null; line =
		 * reader.readLine()) { buf.append(line); }
		 * 
		 * JSONArray obj = new JSONArray(buf.toString()); Set<SearchMeta>
		 * targetDetails = new HashSet<>(); for (int i = 0; i < obj.length();
		 * i++) { JSONObject meta = obj.getJSONObject(i); targetDetails.add(new
		 * SearchMeta(meta.getString("t"), null, null)); }
		 * 
		 * return targetDetails; } }
		 * 
		 * 
		 * private String getSearchCounts(String origin, long countHeadstarts,
		 * long countTradeMarks) throws JSONException { JSONObject obj = new
		 * JSONObject(); obj.put("origin", origin); obj.put("trademarks",
		 * countTradeMarks); obj.put("headstarts", countHeadstarts);
		 * 
		 * return obj.toString(); }
		 */
}
