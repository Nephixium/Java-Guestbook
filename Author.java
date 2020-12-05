package com.javadatabase;

/** Författaren till det inlägg som skapas i gästboken med metoder för att hämta data relaterat till denna
 *  @author Nephixium
 *  @version 1.0
 */

public class Author {

    private String name;
    private String email;
    private String website;

    public Author(String name, String email, String website) {
        this.name = name;
        this.email = email;
        this.website = website;
    }

    /** Returnerar namnet på författaren */
    public String getName() {
        return this.name;
    }
    /** Returnerar författarens mailaddress */
    public String getEmail() {
        return this.email;
    }
    /** Returnerar författarens hemsida */
    public String getWebsite() {
        return this.website;
    }
}
