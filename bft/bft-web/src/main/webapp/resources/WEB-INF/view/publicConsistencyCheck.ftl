<#import 'base_page.ftl' as base>

<@base.fetch_page>
<div ng-controller="verifyPublicCtrl" ng-init="init()">
	<div class="row well well-lg">
		<div class="col-md-8 col-md-offset-2">
			<h1>Australian Trade Mark Search Consistency Checks</h1>
			
			<h2>Last Update Times</h2>
			<table class="table table-striped table-bordered">
				<caption>Contains date times relating to last updates</caption>
				<tr>
					<th>Source of Date Time</th>
					<th>Date Time</th>
				</tr>
				<tr ng-repeat="time in times">
					<td>{{time.source}}</td>
					<td>{{time.dateTime}}</td>
				</tr>
			</table>
		</div>
	</div>
	<div class="row well well-lg">
		<div class="col-md-8 col-md-offset-2">
			<h2>Counts</h2>
			<table class="table table-striped table-bordered">
				<caption>Contains counts from source and target systems
					for trade marks, headstart requests and Article 6ters</caption>
				<tr>
					<th>Origin</th>
					<th>Trade Marks (OPI, incl 6ters)</th>
					<th>Headstarts (Not OPI)</th>
					<th>Article 6ters (OPI) </th>
					<th>Last Change</th>
					<th>Last Index</th>
				</tr>
				<tr ng-repeat="count in counts">
					<td>{{count.serverName}}</td>
					<td>{{count.stats.all.count}}</td>
					<td>{{count.stats.headstart.count}}</td>
					<td>{{count.stats.prohibited.count}}</td>
					<td>{{count.stats.all.lastChangeDate}}</td>
					<td>{{count.stats.all.lastIndexDate}}</td>
				</tr>
			</table>
		</div>
	</div>
	<div class="row well well-lg">
		<div class="col-md-8 col-md-offset-2">
			<h2>Comparison of Individual Records</h2>
			<table class="table table-striped table-bordered">
				<caption>Contains counts of records that are consistent or different between Origin and IPA</caption>
				<tr>
					<th>Origin</th>
					<th>Count of Identical Records</th>
					<th>Count of Different Records</th>
					<th>Count in IPA, but not in Origin</th>
					<th>Count in Origin, but not in IPA</th>
				</tr>
				<tr ng-repeat="count in detailCounts">
					<td>{{count.serverName}}</td>
					<td>{{count.same}}</td>
					<td>{{count.differentNotInCompleted}} (Plus {{count.different}} Pending)</td>
					<td>{{count.inIPANotInTarget}}</td>
					<td>{{count.inTargetNotInIPA}} <button type="button" class="btn btn-primary pull-right" ng-show="count.inTargetNotInIPA > 0"  ng-click="removeOutOfSync()">Remove Deleted Records</button> </td>
				</tr>
			</table>
		</div>
	</div>
	<div class="row well well-lg">
		<div class="col-md-8 col-md-offset-2">
			<h2>Extracts that are different - {{detail.page.totalElements}}</h2>
			<nav>
			  <ul class="pagination">
			    <li ng-repeat="link in detail.links">
			      <button type="button" class="btn" ng-show="link.rel != 'self'" ng-click="getDiff(link.href)" aria-label="{{link.rel}}" >{{link.rel}}</button>
			    </li>
			  </ul>
			</nav>
			<form name="tmForm" novalidate ng-show="detail.page.totalElements > 0">
				<button type="button" class="btn btn-primary pull-right" ng-click="fixOPI()">Fix OPI Differences</button>
			</form>
			<table class="table table-striped table-bordered">
				<caption>Contains change sets</caption>
				<tr>
					<th>Trade Mark Number</th>
					<th>IPA Hash</th>
					<th>Search Hash</th>
					<th>CCheck Hash</th>
					<th>Last Update Date</th>
					<th>Search Last Update Date</th>
					<th>CCheck Last Update Date</th>
					<th>IPA JSON</th>
				</tr>
				<tr ng-repeat="extract in detail.content">
					<td>{{extract.tmNumber}}</td>
					<td>{{extract.ipaHash}}</td>
					<td>{{extract.publicSearchHash}}</td>
					<td>{{extract.ccheckHash}}</td>
					<td>{{extract.ipaHashUpdateDate}}</td>
					<td>{{extract.publicSearchHashUpdateDate}}</td>
					<td>{{extract.ccheckHashUpdateDate}}</td>
					<td ng-show="extract.ipaJson == null">N/A</td>
					<td ng-show="extract.ipaJson != null">
						<button class="btn btn-default" ng-click="open(extract)">View JSON</button>
						
						<!--MODAL WINDOW--> 
		                <script type="text/ng-template" id="myModalContent.html">
		                    <div class="modal-header">
		                        <h3>Extract for - {{ extract.tmNumber }}</h3>
		                    </div>
		                    <div class="modal-body" id="selectme">
								<textarea readonly="readonly" select-on-click>
									{{modifiedString}}
								</textarea>
		                    </div>
		                </script>
		            </td>
				</tr>
			</table>
			<nav>
			  <ul class="pagination">
			    <li ng-repeat="link in detail.links">
			      <button type="button" class="btn" ng-show="link.rel != 'self'" ng-click="getDiff(link.href)" aria-label="{{link.rel}}" >{{link.rel}}</button>
			    </li>
			  </ul>
			</nav>
		</div>
	</div>
</div>
</@base.fetch_page>
