package ga.fundamental.mojo;

import ga.fundamental.merge.YamlMerger;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

@Mojo(name = "mergeYaml")
public class MergeYamlMojo extends AbstractMojo {

	@Parameter(name = "finalYaml", required = true, property = "merge-yaml.finalYaml")
	private String finalYaml;
	@Parameter(name = "files", required = true, property = "merge-yaml.files")
	private List<File> files;

	@Override
	public void execute() throws MojoExecutionException, MojoFailureException {
		for (File file : files) {
			if (!file.exists()) {
				throw new MojoFailureException("File " + file + " does not exist");
			}
		}
		//
		try {
			System.out.println("Merging files " + files.stream().map(File::getName).collect(Collectors.joining(",")) + " into " + finalYaml);
			new YamlMerger(files, finalYaml).merge();
		} catch (Exception e) {
			throw new MojoExecutionException("Failed to merge yaml files", e);
		}
	}
}
