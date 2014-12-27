package Utilities;
import java.util.List;

import Entities.RSSItem;



public interface FeedParser {
	List<RSSItem> parse();
}
