<script src="http://platform.twitter.com/anywhere.js?id=<@s.property value="apiKey"/>&v=1"> </script>

<script type="text/javascript">
    twttr.anywhere(anywhereLoad);

    function anywhereLoad(twitter) {
        twitter.hovercards({ expanded: true });
        twitter.linkifyUsers();
    }
</script>
