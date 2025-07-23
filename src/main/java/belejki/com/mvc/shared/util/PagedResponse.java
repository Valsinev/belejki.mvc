package belejki.com.mvc.shared.util;

import lombok.Data;

import java.util.List;

@Data
public class PagedResponse<T> {
    private List<T> content;
    private int totalPages;
    private int totalElements;
    private boolean last;
    private boolean first;
    private int size;
    private int number;
    private boolean empty;

}

