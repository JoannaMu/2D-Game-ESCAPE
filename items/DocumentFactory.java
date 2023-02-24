package com.group8project.items;

/**
 * Creates a Document
 */
public class DocumentFactory extends ItemFactory {
    /**
     * @return the created document
     */
    public Item createItem() {
        return new Document();
    }
}
