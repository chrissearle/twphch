<script type="text/javascript">
    $(document).ready(function() {
        new Highcharts.Chart({
            chart: {
                renderTo: 'challengeChart',
                defaultSeriesType: 'bar',
                backgroundColor: '#545454',
                borderColor: '#545454',
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
            },
            colors: ['#FF9C26'],
            <@s.if test="!small">
            title: {
                text: '#<@s.property value="challenge.challengeTag"/>',
                style: {
                    color: '#D5D2D6',
                    fontSize: '16px'
                }
            },
            subtitle: {
                text: '<@s.property value="challenge.challengeDescription" escape="false"/>',
                style: {
                    color: '#D5D2D6'
                }
            },
            </@s.if>
            <@s.else>
            title: {
                text: null
            },
            subtitle: {
                text: null
            },
            </@s.else>
            xAxis: {
                title: {
                    text: null
                },
                categories: [
                    <@s.iterator id="image" value="images">
                    <@s.if test="!small">
                    '<@s.property value="#image.photographerName" escape="false"/>',
                    </@s.if>
                    <@s.else>
                    '<@s.property value="#image.rank"/>',
                    </@s.else>
                    </@s.iterator>
                ],
                labels: {
                    <@s.if test="!small">
                    rotation: 30,
                    </@s.if>
                    style: {
                        color: '#D5D2D6'
                    }
                }
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
                    return '<b>' + this.point.rank + ': ' + this.x + '</b><br/>' + this.y + ' votes';
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
                        {y:<@s.property value="#image.voteCount"/>,rank:<@s.property value="#image.rank"/>},
                        </@s.iterator>
                    ],
                    <@s.if test="!small">
                    dataLabels: {
                        enabled: true,
                        color: '#D5D2D6',
                        align: 'left',
                        x: +10,
                        y: -9,
                        formatter: function() {
                            if (this.y > 0) {
                                return this.point.rank;
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
