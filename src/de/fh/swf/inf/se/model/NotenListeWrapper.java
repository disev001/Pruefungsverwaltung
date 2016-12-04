package de.fh.swf.inf.se.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Created by dsee on 04.12.2016.
 */

@XmlRootElement(name="notenliste")
public class NotenListeWrapper {
    private List<Fach> noten;

    @XmlElement(name="note")
    public  List<Fach> getFach(){
        return noten;
    }

    public void setNoten(List<Fach> notenliste){
        this.noten = notenliste;
    }
}
