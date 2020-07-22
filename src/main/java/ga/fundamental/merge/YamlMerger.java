package ga.fundamental.merge;

import com.amihaiemil.eoyaml.Yaml;
import com.amihaiemil.eoyaml.YamlMapping;
import com.amihaiemil.eoyaml.YamlPrinter;
import com.amihaiemil.eoyaml.extensions.MergedYamlMapping;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class YamlMerger {

	private final List<File> sourceFiles;
	private final String mergedFileName;

	public YamlMerger(List<File> sourceFiles, String mergedFileName) {
		this.sourceFiles = sourceFiles;
		this.mergedFileName = mergedFileName;
	}

	public void merge() throws IOException {
		YamlMapping mergedMapping = sourceFiles.stream()
				.map(this::createYamlMapping)
				.reduce(MergedYamlMapping::new)
				.orElseThrow(RuntimeException::new);
		File mergedFile = new File(mergedFileName);
		mergedFile.getParentFile().mkdirs();
		YamlPrinter filePrinter = Yaml.createYamlPrinter(new FileWriter(mergedFile));

		filePrinter.print(mergedMapping);
	}

	private YamlMapping createYamlMapping(File yamlFile) {
		try {
			return Yaml.createYamlInput(yamlFile).readYamlMapping();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
