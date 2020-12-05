package com.javadatabase;

/** Kommentaren som skrivs av en författare i gästboken med metoder som återger data tillhörande detta objekt
 *  @author Nephixium
 *  @version 1.0
 */

public class Comment {

    private String writtenComment;
    private Author author;

    public Comment(Author author, String comment) {
        this.author = author;
        this.writtenComment = comment;
    }

    /** Returnerar kommentaren */
    public String getComment() {
        return this.writtenComment;
    }

}
