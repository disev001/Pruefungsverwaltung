package de.fh.swf.inf.se.model;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Noteneintrag")
public class NotenListeWrapper {

    private List<Fach> data;

    public List<Fach> getNoteliste()
    {
        return data;
    }

    public void setNoteliste(List<Fach> data)
    {
        this.data = data;
    }

}
