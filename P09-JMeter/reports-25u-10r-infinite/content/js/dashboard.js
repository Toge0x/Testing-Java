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
    createTable($("#statisticsTable"), {"supportsControllersDiscrimination": true, "overall": {"data": ["Total", 65126, 0, 0.0, 1.0383103522402815, 0, 15, 1.0, 2.0, 2.0, 4.0, 797.4091487902831, 2380.4640365004225, 486.57093838387084], "isController": false}, "titles": ["Label", "#Samples", "FAIL", "Error %", "Average", "Min", "Max", "Median", "90th pct", "95th pct", "99th pct", "Transactions/s", "Received", "Sent"], "items": [{"data": ["Reptiles", 1816, 0, 0.0, 1.120594713656389, 0, 8, 1.0, 2.0, 2.0, 4.0, 22.293971052211596, 80.81564506426704, 5.442864026418847], "isController": false}, {"data": ["Main Catalog", 5437, 0, 0.0, 0.9255103917601636, 0, 9, 1.0, 2.0, 2.0, 3.0, 66.59480910794558, 314.517871920281, 34.317614109599106], "isController": false}, {"data": ["Birds", 1799, 0, 0.0, 1.122290161200667, 0, 11, 1.0, 2.0, 2.0, 3.0, 22.28912677173159, 80.75455109680097, 5.376381164665725], "isController": false}, {"data": ["Login", 5422, 0, 0.0, 1.95702692733308, 0, 15, 2.0, 3.0, 4.0, 6.0, 66.51210147327618, 341.7838651878703, 104.76955046522896], "isController": false}, {"data": ["Login-0", 5422, 0, 0.0, 1.000000000000002, 0, 11, 1.0, 2.0, 2.0, 3.0, 66.51291739247773, 12.536126031980666, 60.6670555122795], "isController": false}, {"data": ["Login-1", 5422, 0, 0.0, 0.9066765031353774, 0, 13, 1.0, 2.0, 2.0, 3.0, 66.51291739247773, 329.251931896943, 44.10378018505116], "isController": false}, {"data": ["Home-0", 5441, 0, 0.0, 0.4598419408197015, 0, 5, 0.0, 1.0, 1.0, 1.0, 66.62992897379377, 9.49997034196669, 12.53784986912197], "isController": false}, {"data": ["Home-1", 5441, 0, 0.0, 0.3848557250505423, 0, 2, 0.0, 1.0, 1.0, 1.0, 66.63237689359148, 87.91048943675986, 12.603381178282328], "isController": false}, {"data": ["Dogs", 1804, 0, 0.0, 1.1812638580931238, 0, 10, 1.0, 2.0, 2.0, 4.0, 22.266107134040976, 93.41327758578129, 5.349084331029376], "isController": false}, {"data": ["Sign In", 5430, 0, 0.0, 1.1095764272559863, 0, 11, 1.0, 2.0, 2.0, 4.0, 66.53759435349475, 254.90559672458582, 38.921893572014994], "isController": false}, {"data": ["Sign Out", 5417, 0, 0.0, 1.88369946464833, 0, 12, 2.0, 3.0, 4.0, 6.0, 66.55608797149527, 341.78172082335055, 69.19155887163656], "isController": false}, {"data": ["Sign Out-0", 5417, 0, 0.0, 0.9008676389145279, 0, 9, 1.0, 2.0, 2.0, 3.0, 66.55690572436077, 21.578996777820098, 34.90337731834155], "isController": false}, {"data": ["Home", 5441, 0, 0.0, 0.8932181584267592, 0, 7, 1.0, 2.0, 2.0, 3.0, 66.62911304049669, 97.40603732580423, 25.140460160756053], "isController": false}, {"data": ["Sign Out-1", 5417, 0, 0.0, 0.9027136791582044, 0, 12, 1.0, 2.0, 2.0, 3.0, 66.55690572436077, 320.2069234056507, 34.28903168732876], "isController": false}]}, function(index, item){
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
    createTable($("#top5ErrorsBySamplerTable"), {"supportsControllersDiscrimination": false, "overall": {"data": ["Total", 65126, 0, "", "", "", "", "", "", "", "", "", ""], "isController": false}, "titles": ["Sample", "#Samples", "#Errors", "Error", "#Errors", "Error", "#Errors", "Error", "#Errors", "Error", "#Errors", "Error", "#Errors"], "items": [{"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}]}, function(index, item){
        return item;
    }, [[0, 0]], 0);

});
