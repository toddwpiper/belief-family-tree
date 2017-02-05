<#import 'base_page.ftl' as base>

<@base.fetch_page>
<div ng-controller="beliefCtrl" ng-init="init()">
	<div class="row well well-lg">
		<div class="col-md-8 col-md-offset-2">
			<h1Belief Manager</h1>
			
			<table class="table table-striped table-bordered">
				<caption>Contains Belief Datacaption>
				<tr>
					<th>Id</th>
					<th>Name</th>
				</tr>
				<tr>
					<td>{{belief.id}}</td>
					<td>{{belief.name}}</td>
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
</div>
</@base.fetch_page>
