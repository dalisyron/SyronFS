package repository.query.update;

import repository.query.Query;

public class UpdateArtistQuery extends Query {

    private final String name;
    private final String fieldToUpdate;
    private final String fieldValue;

    public UpdateArtistQuery(String name, String fieldToUpdate, String fieldValue) {
        this.name = name;
        this.fieldToUpdate = fieldToUpdate;
        this.fieldValue = fieldValue;
    }

    public String getName() {
        return name;
    }

    public String getFieldToUpdate() {
        return fieldToUpdate;
    }

    public String getFieldValue() {
        return fieldValue;
    }
}
