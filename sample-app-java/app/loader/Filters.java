package loader;

import com.ybrikman.ping.javaapi.dedupe.CacheFilter;
import com.ybrikman.ping.javaapi.dedupe.DedupingCache;
import play.api.mvc.EssentialFilter;
import play.http.HttpFilters;
import play.libs.F;
import play.libs.ws.WSResponse;

import com.mohiva.play.htmlcompressor.java.HTMLCompressorFilter;
import com.mohiva.play.xmlcompressor.java.XMLCompressorFilter;
import play.filters.gzip.GzipFilter;

import javax.inject.Inject;

/**
 * Custom Filters for this app. Play knows to load this class because we put it into conf/application.conf
 */
public class Filters implements HttpFilters {

  private final CacheFilter<String, F.Promise<WSResponse>> cacheFilter;

  @Inject
  public Filters(DedupingCache<String, F.Promise<WSResponse>> cache) {
    cacheFilter = new CacheFilter<>(cache);
  }
  
  @Inject
  GzipFilter gzipFilter;
  // new GzipFilter(shouldGzip = (request, response) =>
  // response.headers.get("Content-Type").exists(_.startsWith("text/html")))
  
  @Override
  public EssentialFilter[] filters() {
    return new EssentialFilter[]{gzipFilter, new HTMLCompressorFilter(), new XMLCompressorFilter(), cacheFilter};
  }
}
