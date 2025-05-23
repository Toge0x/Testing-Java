/*
   Licensed to the Apache Software Foundation (ASF) under one or more
   contributor license agreements.  See the NOTICE file distributed with
   this work for additional information regarding copyright ownership.
   The ASF licenses this file to You under the Apache License, Version 2.0
   (the "License"); you may not use this file except in compliance with
   the License.  You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/
var showControllersOnly = false;
var seriesFilter = "";
var filtersOnlySampleSeries = true;

/*
 * Add header in statistics table to group metrics by category
 * format
 *
 */
function summaryTableHeader(header) {
    var newRow = header.insertRow(-1);
    newRow.className = "tablesorter-no-sort";
    var cell = document.createElement('th');
    cell.setAttribute("data-sorter", false);
    cell.colSpan = 1;
    cell.innerHTML = "Requests";
    newRow.appendChild(cell);

    cell = document.createElement('th');
    cell.setAttribute("data-sorter", false);
    cell.colSpan = 3;
    cell.innerHTML = "Executions";
    newRow.appendChild(cell);

    cell = document.createElement('th');
    cell.setAttribute("data-sorter", false);
    cell.colSpan = 7;
    cell.innerHTML = "Response Times (ms)";
    newRow.appendChild(cell);

    cell = document.createElement('th');
    cell.setAttribute("data-sorter", false);
    cell.colSpan = 1;
    cell.innerHTML = "Throughput";
    newRow.appendChild(cell);

    cell = document.createElement('th');
    cell.setAttribute("data-sorter", false);
    cell.colSpan = 2;
    cell.innerHTML = "Network (KB/sec)";
    newRow.appendChild(cell);
}

/*
 * Populates the table identified by id parameter with the specified data and
 * format
 *
 */
function createTable(table, info, formatter, defaultSorts, seriesIndex, headerCreator) {
    var tableRef = table[0];

    // Create header and populate it with data.titles array
    var header = tableRef.createTHead();

    // Call callback is available
    if(headerCreator) {
        headerCreator(header);
    }

    var newRow = header.insertRow(-1);
    for (var index = 0; index < info.titles.length; index++) {
        var cell = document.createElement('th');
        cell.innerHTML = info.titles[index];
        newRow.appendChild(cell);
    }

    var tBody;

    // Create overall body if defined
    if(info.overall){
        tBody = document.createElement('tbody');
        tBody.className = "tablesorter-no-sort";
        tableRef.appendChild(tBody);
        var newRow = tBody.insertRow(-1);
        var data = info.overall.data;
        for(var index=0;index < data.length; index++){
            var cell = newRow.insertCell(-1);
            cell.innerHTML = formatter ? formatter(index, data[index]): data[index];
        }
    }

    // Create regular body
    tBody = document.createElement('tbody');
    tableRef.appendChild(tBody);

    var regexp;
    if(seriesFilter) {
        regexp = new RegExp(seriesFilter, 'i');
    }
    // Populate body with data.items array
    for(var index=0; index < info.items.length; index++){
        var item = info.items[index];
        if((!regexp || filtersOnlySampleSeries && !info.supportsControllersDiscrimination || regexp.test(item.data[seriesIndex]))
                &&
                (!showControllersOnly || !info.supportsControllersDiscrimination || item.isController)){
            if(item.data.length > 0) {
                var newRow = tBody.insertRow(-1);
                for(var col=0; col < item.data.length; col++){
                    var cell = newRow.insertCell(-1);
                    cell.innerHTML = formatter ? formatter(col, item.data[col]) : item.data[col];
                }
            }
        }
    }

    // Add support of columns sort
    table.tablesorter({sortList : defaultSorts});
}

$(document).ready(function() {

    // Customize table sorter default options
    $.extend( $.tablesorter.defaults, {
        theme: 'blue',
        cssInfoBlock: "tablesorter-no-sort",
        widthFixed: true,
        widgets: ['zebra']
    });

    var data = {"OkPercent": 100.0, "KoPercent": 0.0};
    var dataset = [
        {
            "label" : "FAIL",
            "data" : data.KoPercent,
            "color" : "#FF6347"
        },
        {
            "label" : "PASS",
            "data" : data.OkPercent,
            "color" : "#9ACD32"
        }];
    $.plot($("#flot-requests-summary"), dataset, {
        series : {
            pie : {
                show : true,
                radius : 1,
                label : {
                    show : true,
                    radius : 3 / 4,
                    formatter : function(label, series) {
                        return '<div style="font-size:8pt;text-align:center;padding:2px;color:white;">'
                            + label
                            + '<br/>'
                            + Math.round10(series.percent, -2)
                            + '%</div>';
                    },
                    background : {
                        opacity : 0.5,
                        color : '#000'
                    }
                }
            }
        },
        legend : {
            show : true
        }
    });

    // Creates APDEX table
    createTable($("#apdexTable"), {"supportsControllersDiscrimination": true, "overall": {"data": [1.0, 500, 1500, "Total"], "isController": false}, "titles": ["Apdex", "T (Toleration threshold)", "F (Frustration threshold)", "Label"], "items": [{"data": [1.0, 500, 1500, "Reptiles"], "isController": false}, {"data": [1.0, 500, 1500, "Main Catalog"], "isController": false}, {"data": [1.0, 500, 1500, "Birds"], "isController": false}, {"data": [1.0, 500, 1500, "Login"], "isController": false}, {"data": [1.0, 500, 1500, "Login-0"], "isController": false}, {"data": [1.0, 500, 1500, "Login-1"], "isController": false}, {"data": [1.0, 500, 1500, "Home-0"], "isController": false}, {"data": [1.0, 500, 1500, "Home-1"], "isController": false}, {"data": [1.0, 500, 1500, "Dogs"], "isController": false}, {"data": [1.0, 500, 1500, "Sign In"], "isController": false}, {"data": [1.0, 500, 1500, "Sign Out"], "isController": false}, {"data": [1.0, 500, 1500, "Sign Out-0"], "isController": false}, {"data": [1.0, 500, 1500, "Home"], "isController": false}, {"data": [1.0, 500, 1500, "Sign Out-1"], "isController": false}]}, function(index, item){
        switch(index){
            case 0:
                item = item.toFixed(3);
                break;
            case 1:
            case 2:
                item = formatDuration(item);
                break;
        }
        return item;
    }, [[0, 0]], 3);

    // Create statistics table
    createTable($("#statisticsTable"), {"supportsControllersDiscrimination": true, "overall": {"data": ["Total", 12000, 0, 0.0, 1.5165000000000002, 0, 15, 1.0, 3.0, 4.0, 5.0, 493.03586835942315, 1473.5767200403675, 300.69735205072107], "isController": false}, "titles": ["Label", "#Samples", "FAIL", "Error %", "Average", "Min", "Max", "Median", "90th pct", "95th pct", "99th pct", "Transactions/s", "Received", "Sent"], "items": [{"data": ["Reptiles", 350, 0, 0.0, 1.5628571428571427, 0, 9, 1.0, 3.0, 3.0, 4.0, 14.551199434581966, 52.74809795035962, 3.5525389244584873], "isController": false}, {"data": ["Main Catalog", 1000, 0, 0.0, 1.3719999999999997, 0, 9, 1.0, 2.0, 3.0, 4.0, 41.60945366787334, 197.27411755450836, 21.385796352931386], "isController": false}, {"data": ["Birds", 325, 0, 0.0, 1.590769230769231, 0, 5, 1.0, 3.0, 3.0, 4.0, 14.221327615630333, 51.52453657616068, 3.430339766660832], "isController": false}, {"data": ["Login", 1000, 0, 0.0, 2.8540000000000014, 0, 15, 3.0, 5.0, 5.0, 6.0, 41.61291664932795, 213.8351244226208, 65.5484712454746], "isController": false}, {"data": ["Login-0", 1000, 0, 0.0, 1.4690000000000007, 0, 12, 1.0, 3.0, 3.0, 3.0, 41.61984434178216, 7.844365193324177, 37.96185021018021], "isController": false}, {"data": ["Login-1", 1000, 0, 0.0, 1.3159999999999994, 0, 10, 1.0, 2.0, 3.0, 3.990000000000009, 41.61984434178216, 206.0263583676697, 27.597533503974695], "isController": false}, {"data": ["Home-0", 1000, 0, 0.0, 0.6680000000000009, 0, 6, 1.0, 1.0, 1.0, 2.0, 41.78156597309267, 5.957137336007353, 7.805482002590456], "isController": false}, {"data": ["Home-1", 1000, 0, 0.0, 0.5980000000000008, 0, 2, 1.0, 1.0, 1.0, 2.0, 41.785057663379575, 55.128528225806456, 7.846940027995989], "isController": false}, {"data": ["Dogs", 325, 0, 0.0, 1.6030769230769224, 0, 5, 2.0, 3.0, 3.0, 4.0, 14.251885634099281, 59.791113949307146, 3.4237928378793194], "isController": false}, {"data": ["Sign In", 1000, 0, 0.0, 1.5739999999999998, 0, 10, 1.0, 3.0, 3.0, 4.0, 41.664930627890506, 159.62062778634223, 24.37235688096329], "isController": false}, {"data": ["Sign Out", 1000, 0, 0.0, 2.7750000000000035, 0, 14, 3.0, 5.0, 5.0, 7.0, 41.61118508655127, 213.93041699608855, 43.238572528295606], "isController": false}, {"data": ["Sign Out-0", 1000, 0, 0.0, 1.3569999999999989, 0, 10, 1.0, 2.0, 3.0, 3.0, 41.61638020724957, 13.492810770319197, 21.82421501102834], "isController": false}, {"data": ["Home", 1000, 0, 0.0, 1.3360000000000019, 0, 8, 1.0, 2.0, 3.0, 3.0, 41.77807486631016, 61.075955151236634, 15.650458514371659], "isController": false}, {"data": ["Sign Out-1", 1000, 0, 0.0, 1.2940000000000003, 0, 10, 1.0, 2.0, 3.0, 4.0, 41.61464835622139, 200.45597300249688, 21.418864440282977], "isController": false}]}, function(index, item){
        switch(index){
            // Errors pct
            case 3:
                item = item.toFixed(2) + '%';
                break;
            // Mean
            case 4:
            // Mean
            case 7:
            // Median
            case 8:
            // Percentile 1
            case 9:
            // Percentile 2
            case 10:
            // Percentile 3
            case 11:
            // Throughput
            case 12:
            // Kbytes/s
            case 13:
            // Sent Kbytes/s
                item = item.toFixed(2);
                break;
        }
        return item;
    }, [[0, 0]], 0, summaryTableHeader);

    // Create error table
    createTable($("#errorsTable"), {"supportsControllersDiscrimination": false, "titles": ["Type of error", "Number of errors", "% in errors", "% in all samples"], "items": []}, function(index, item){
        switch(index){
            case 2:
            case 3:
                item = item.toFixed(2) + '%';
                break;
        }
        return item;
    }, [[1, 1]]);

        // Create top5 errors by sampler
    createTable($("#top5ErrorsBySamplerTable"), {"supportsControllersDiscrimination": false, "overall": {"data": ["Total", 12000, 0, "", "", "", "", "", "", "", "", "", ""], "isController": false}, "titles": ["Sample", "#Samples", "#Errors", "Error", "#Errors", "Error", "#Errors", "Error", "#Errors", "Error", "#Errors", "Error", "#Errors"], "items": [{"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}]}, function(index, item){
        return item;
    }, [[0, 0]], 0);

});
