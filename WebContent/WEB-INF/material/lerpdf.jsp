	<script>
	function getDocumentUrl(document) {
		return "php/services/view.php?doc={doc}&format={format}&page={page}"
				.replace("{doc}", document);
	}
	var startDocument = "Paper";
	var td = (function() {
		try {
			return 'ontouchstart' in document.documentElement;
		} catch (e) {
			return false;
		}
	})();
	if (td) {
		$('#viewercontainer').css({
			width : '900px',
			height : '500px'
		});
	}
	var fp = new FlexPaperViewer('FlexPaperViewer', 'documentViewer', {
		config : {
			PDFFile : "${pathPDF}",
			key : '$8ed9d54d152a6cfe113',
			Scale : 0.6,
			ZoomTransition : 'easeOut',
			ZoomTime : 0.5,
			ZoomInterval : 0.2,
			FitPageOnLoad : true,
			FitWidthOnLoad : false,
			FullScreenAsMaxWindow : false,
			ProgressiveLoading : false,
			MinZoomSize : 0.2,
			MaxZoomSize : 5,
			SearchMatchAll : true,
			InitViewMode : 'Portrait',
			RenderingOrder : 'html,flash',
			EnableCornerDragging : true,
			ViewModeToolsVisible : true,
			ZoomToolsVisible : true,
			NavToolsVisible : true,
			CursorToolsVisible : true,
			SearchToolsVisible : true,
			WMode : 'transparent',
			localeChain : 'en_US'
		}
	});
</script>


<div
	style="position: absolute; left: 10px; top: 10px; width: 80%; height: 98%"
	id="viewercontainer">
	<div id="documentViewer" class="flexpaper_viewer"></div>
</div>

<script type="text/javascript">
	if (td) {
		$('#infobox').hide();
	}
	var gaJsHost = (("https:" == document.location.protocol) ? "https://ssl."
			: "http://www.");
	document
			.write(unescape("%3Cscript src='"
					+ gaJsHost
					+ "google-analytics.com/ga.js' type='text/javascript'%3E%3C/script%3E"));
</script>
<script type="text/javascript">
	try {
		var pageTracker = _gat._getTracker("UA-10148899-3");
		pageTracker._trackPageview();
	} catch (err) {
	}
</script>

<!-- Piwik -->
<script type="text/javascript">
	var pkBaseURL = (("https:" == document.location.protocol) ? "https://www.devaldi.com/piwik/"
			: "http://www.devaldi.com/piwik/");
	document.write(unescape("%3Cscript src='" + pkBaseURL
			+ "piwik.js' type='text/javascript'%3E%3C/script%3E"));
</script>
<script type="text/javascript">
	try {
		var piwikTracker = Piwik.getTracker(pkBaseURL + "piwik.php", 1);
		piwikTracker.trackPageView();
		piwikTracker.enableLinkTracking();
	} catch (err) {
	}
</script>
<noscript>
	<p>
		<img src="http://www.devaldi.com/piwik/piwik.php?idsite=1"
			style="border: 0" alt="" />
	</p>
</noscript>
<!-- End Piwik Tracking Code -->