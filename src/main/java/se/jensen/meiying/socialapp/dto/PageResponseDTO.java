package se.jensen.meiying.socialapp.dto;

import java.util.List;

/**
 * Generic Data Transfer Object used to represent paginated responses.
 * <p>
 * This class is used to send pageable data from the backend to the client,
 * including metadata such as page number, page size and total elements.
 *
 * @param <T> the type of content contained in the page
 */
public class PageResponseDTO<T> {

    /**
     * List of elements contained in the current page.
     */
    private List<T> content;

    /**
     * Current page index (zero-based).
     */
    private int page;

    /**
     * Number of elements per page.
     */
    private int size;

    /**
     * Total number of elements available.
     */
    private long totalElements;

    /**
     * Total number of pages.
     */
    private int totalPages;

    /**
     * Indicates whether this is the last page.
     */
    private boolean last;

    /**
     * Creates a new PageResponseDTO with pagination metadata.
     *
     * @param content       list of items in the current page
     * @param page          current page index
     * @param size          number of elements per page
     * @param totalElements total number of elements
     * @param totalPages    total number of pages
     * @param last          indicates if this is the last page
     */
    public PageResponseDTO(List<T> content,
                           int page,
                           int size,
                           long totalElements,
                           int totalPages,
                           boolean last) {
        this.content = content;
        this.page = page;
        this.size = size;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
        this.last = last;
    }

    /**
     * Returns the content of the current page.
     *
     * @return list of elements
     */
    public List<T> getContent() {
        return content;
    }

    /**
     * Returns the current page index.
     *
     * @return page number
     */
    public int getPage() {
        return page;
    }

    /**
     * Returns the page size.
     *
     * @return number of elements per page
     */
    public int getSize() {
        return size;
    }

    /**
     * Returns the total number of elements.
     *
     * @return total elements count
     */
    public long getTotalElements() {
        return totalElements;
    }

    /**
     * Returns the total number of pages.
     *
     * @return total page count
     */
    public int getTotalPages() {
        return totalPages;
    }

    /**
     * Indicates whether this page is the last one.
     *
     * @return true if last page, false otherwise
     */
    public boolean isLast() {
        return last;
    }
}
