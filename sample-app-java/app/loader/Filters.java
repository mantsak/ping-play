package loader;

import com.ybrikman.ping.javaapi.dedupe.CacheFilter;
import com.ybrikman.ping.javaapi.dedupe.DedupingCache;
import play.api.mvc.EssentialFilter;
import play.http.HttpFilters;
import play.libs.F;
import play.libs.ws.WSResponse;

import javax.inject.Inject;

public class Filters implements HttpFilters {

  private final CacheFilter<String, F.Promise<WSResponse>> cacheFilter;

  @Inject
  public Filters(DedupingCache<String, F.Promise<WSResponse>> cache) {
    cacheFilter = new CacheFilter<>(cache);
  }

  @Override
  public EssentialFilter[] filters() {
    return new EssentialFilter[]{cacheFilter};
  }
}
