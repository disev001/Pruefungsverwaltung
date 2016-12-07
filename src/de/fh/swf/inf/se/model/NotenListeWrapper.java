package de.fh.swf.inf.se.model;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * JAXB Wrapper Klasse für Persistierung
 */
@XmlRootElement(name = "Noteneintrag")
public class NotenListeWrapper {

    private List<Fach> data;

    public List<Fach> getNoteliste() {
        return data;
    }

    public void setNoteliste(List<Fach> data) {
        this.data = data;
    }

}
