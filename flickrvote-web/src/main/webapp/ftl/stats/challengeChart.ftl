<script type="text/javascript">
    var challengeChartRanks = [];

    <@s.if test="!small">
    <@s.iterator id="image" value="images">
    challengeChartRanks[<@s.property value="#image.voteCount"/>] = <@s.property value="#image.rank"/>;
    </@s.iterator>
    </@s.if>

    $(document).ready(function() {
        new Highcharts.Chart({
            chart: {
                renderTo: 'challengeChart',
                defaultSeriesType: 'bar',
                <@s.if test="!small">
                marginLeft: 150,
                marginTop: 50,
                </@s.if>
                <@s.else>
                marginLeft: 30,
                marginRight: 10,
                marginTop: 10,
                marginBottom: 30,
                </@s.else>
                backgroundColor: '#545454',
                borderColor: '#545454'
            },
            colors: ['#FF9C26'],
            title: {
                <@s.if test="!small">
                text: '#<@s.property value="challenge.challengeTag"/>',
                style: {
                    color: '#D5D2D6',
                    fontSize: '16px'
                }
                </@s.if>
                <@s.else>
                text: null
                </@s.else>
            },
            subtitle: {
                <@s.if test="!small">
                text: '<@s.property value="challenge.challengeDescription" escape="false"/>',
                style: {
                    color: '#D5D2D6'
                }
                </@s.if>
                <@s.else>
                text: null
                </@s.else>
            },
            xAxis: {
                title: {
                    text: null
                },
                <@s.if test="!small">
                categories: [
                    <@s.iterator id="image" value="images">
                    '<@s.property value="#image.photographerName" escape="false"/>',
                    </@s.iterator>
                ],
                labels: {
                    rotation: 30,
                    style: {
                        color: '#D5D2D6'
                    }
                }
                </@s.if>
                <@s.else>
                labels: {
                    style: {
                        color: '#D5D2D6'
                    }
                },
                categories: [
                    <@s.iterator id="image" value="images">
                    '<@s.property value="#image.rank"/>',
                    </@s.iterator>
                ],
                </@s.else>
            },
            yAxis: {
                title: {
                    text: null
                },
                labels: {
                    style: {
                        color: '#D5D2D6'
                    }
                }
            },
            legend: {
                <@s.if test="!small">
                itemStyle: {
                    cursor: 'pointer',
                    color: '#D5D2D6'
                }
                </@s.if>
                <@s.else>
                enabled: false
                </@s.else>
            },
            credits: {
                enabled: false
            },
            tooltip: {
                formatter: function() {
                <@s.if test="!small">
                    return '<b>' + this.x + '</b><br/>' + this.y + ' votes';
                </@s.if>
                <@s.else>
                    return this.y + ' votes';
                </@s.else>
                }
            },
            series: [
                {
                    name: 'Votes',
                    data: [
                        <@s.iterator id="image" value="images">
                        <@s.property value="#image.voteCount"/>,
                        </@s.iterator>
                    ],
                    <@s.if test="!small">
                    dataLabels: {
                        enabled: true,
                        color: '#545454',
                        align: 'right',
                        x: -3,
                        y: -6,
                        formatter: function() {
                            if (this.y > 0) {
                                return challengeChartRanks[this.y];
                            }
                        },
                        style: {
                            font: 'sans-serif'
                        }
                    }
                    </@s.if>
                }
            ]
        });
    });
</script>
