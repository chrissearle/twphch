<script type="text/javascript">
    $(document).ready(function() {
        $('#challengeVsEntriesChart').height(<@s.property value="challengeVsEntriesHeight"/>);

        new Highcharts.Chart({
            chart: {
                renderTo: 'challengeVsEntriesChart',
                defaultSeriesType: 'bar',
                backgroundColor: '#545454',
                borderColor: '#545454',
                marginLeft: 150,
                marginTop: 100,
            },
            colors: ['#FF9C26', '#CCCCCC'],
            title: {
                text: 'Challenges vs. Entries',
                style: {
                    color: '#D5D2D6',
                    fontSize: '16px'
                }
            },
            subtitle: {
                text: 'Mouse over bar to see challenge title',
                style: {
                    color: '#D5D2D6'
                }
            },
            xAxis: {
                title: {
                    text: null
                },
                categories: [
                    <@s.iterator id="challenge" value="challenges">
                    '<@s.property value="#challenge.challengeTag"/>',
                    </@s.iterator>
                ],
                labels: {
                    rotation: 30,
                    style: {
                        color: '#D5D2D6'
                    }
                }
            },
            yAxis: [
                {
                    title: {
                        text: 'Number of entries',
                        style: {
                            color: '#D5D2D6'
                        }
                    },
                    labels: {
                        style: {
                            color: '#D5D2D6'
                        }
                    },
                    opposite: true
                },
                {
                    title: {
                        text: 'Number of votes',
                        style: {
                            color: '#D5D2D6'
                        }
                    },
                    labels: {
                        style: {
                            color: '#D5D2D6'
                        }
                    },
                    min: 0
                }
            ],
            legend: {
                itemStyle: {
                    cursor: 'pointer',
                    color: '#D5D2D6'
                }
            },
            credits: {
                enabled: false
            },
            tooltip: {
                formatter: function() {
                    var unit = {
                        'Number of Entries': 'entries',
                        'Number of votes': 'votes'
                    }[this.series.name];

                    return '<b>' + this.x + ': ' + this.point.title + '</b><br/>' + this.y + ' ' + unit;
                }
            },
            series: [
                {
                    name: 'Number of Entries',
                    data: [
                        <@s.iterator id="imageCount" value="imageCounts">
                        {y:<@s.property value="#imageCount.statisticValue"/>,title:'<@s.property value="#imageCount.title" escape="false"/>'},
                        </@s.iterator>
                    ]
                },
                {
                    name: 'Number of votes',
                    type: 'line',
                    data: [
                        <@s.iterator id="imageVoteCount" value="imageVoteCounts">
                        {y:<@s.property value="#imageVoteCount.statisticValue"/>,title:'<@s.property value="#imageVoteCount.title" escape="false"/>'},
                        </@s.iterator>
                    ],
                    yAxis: 1
                }
            ]
        });
    });
</script>
