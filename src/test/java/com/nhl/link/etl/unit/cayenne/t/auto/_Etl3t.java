package com.nhl.link.etl.unit.cayenne.t.auto;

import org.apache.cayenne.CayenneDataObject;
import org.apache.cayenne.exp.Property;

import com.nhl.link.etl.unit.cayenne.t.Etl2t;
import com.nhl.link.etl.unit.cayenne.t.Etl5t;

/**
 * Class _Etl3t was generated by Cayenne.
 * It is probably a good idea to avoid changing this class manually,
 * since it may be overwritten next time code is regenerated.
 * If you need to make any customizations, please use subclass.
 */
public abstract class _Etl3t extends CayenneDataObject {

    @Deprecated
    public static final String NAME_PROPERTY = "name";
    @Deprecated
    public static final String PHONE_NUMBER_PROPERTY = "phoneNumber";
    @Deprecated
    public static final String E2_PROPERTY = "e2";
    @Deprecated
    public static final String E5_PROPERTY = "e5";

    public static final String ID_PK_COLUMN = "id";

    public static final Property<String> NAME = new Property<String>("name");
    public static final Property<String> PHONE_NUMBER = new Property<String>("phoneNumber");
    public static final Property<Etl2t> E2 = new Property<Etl2t>("e2");
    public static final Property<Etl5t> E5 = new Property<Etl5t>("e5");

    public void setName(String name) {
        writeProperty("name", name);
    }
    public String getName() {
        return (String)readProperty("name");
    }

    public void setPhoneNumber(String phoneNumber) {
        writeProperty("phoneNumber", phoneNumber);
    }
    public String getPhoneNumber() {
        return (String)readProperty("phoneNumber");
    }

    public void setE2(Etl2t e2) {
        setToOneTarget("e2", e2, true);
    }

    public Etl2t getE2() {
        return (Etl2t)readProperty("e2");
    }


    public void setE5(Etl5t e5) {
        setToOneTarget("e5", e5, true);
    }

    public Etl5t getE5() {
        return (Etl5t)readProperty("e5");
    }


}