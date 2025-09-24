package nextgen.generator;

import nextgen.model.Project;

public abstract class Generator {

    public abstract void generate(Project project, String directory) throws Exception;
}
