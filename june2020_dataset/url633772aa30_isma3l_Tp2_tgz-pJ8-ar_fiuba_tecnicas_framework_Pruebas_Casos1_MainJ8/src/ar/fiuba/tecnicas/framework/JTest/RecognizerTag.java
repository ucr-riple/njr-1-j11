package ar.fiuba.tecnicas.framework.JTest;


import java.util.List;

public class RecognizerTag {
    private List<String> tags;

    public RecognizerTag(List<String> tags) {
        this.tags = tags;
    }

    public boolean validate(List<String> tagsTest) {
        if(tags.isEmpty()) {
            return true;
        }
        else {
            for(String tag:tagsTest) {
                if(tags.contains(tag))
                    return true;
            }
            return false;
        }
    }

}
