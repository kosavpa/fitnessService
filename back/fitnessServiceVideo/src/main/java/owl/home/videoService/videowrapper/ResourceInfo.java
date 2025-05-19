package owl.home.videoService.videowrapper;


import lombok.Builder;
import lombok.Data;
import lombok.SneakyThrows;

import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Data
public class ResourceInfo {
    private static final Pattern fileSearcherPattern = Pattern
            .compile("\\d{1,2}/.+\\.(jpg|mp4)");

    private static final String IMG_EXT = ".jpg";

    private static final String VID_EXT = ".mp4";

    private ResourceInfo() {
    }

    @SneakyThrows
    public static List<InfoWrapper> getInfos() {
        String packageToScan = "video";

        Stream<Path> walk = Files.walk(
                Path.of(
                        Objects.requireNonNull(ResourceInfo.class.getClassLoader().getResource(packageToScan)).toURI()
                )
                ,
                FileVisitOption.FOLLOW_LINKS
        );

        try (walk) {
            Map<String, String> collect = walk.filter(path -> !Files.isDirectory(path))
                    .map(Path::toAbsolutePath)
                    .map(Path::toString)
                    .map(path -> path.replace("\\", "/"))
                    .map(ResourceInfo::getPathWithUpDir)
                    .filter(Objects::nonNull)
                    .collect(
                            Collectors.toMap(
                                    path -> path.split("/")[0],
                                    path -> path.split("/")[1],
                                    (path1, path2) -> path1 + "|" + path2));

            return collect.entrySet()
                    .stream()
                    .map(ResourceInfo::createInfo)
                    .toList();
        }
    }

    private static InfoWrapper createInfo(Map.Entry<String, String> entry) {
        InfoWrapper infoWrapper = InfoWrapper
                .builder()
                .dirName(entry.getKey())
                .build();

        for (String path : entry.getValue().split("\\|")) {
            if (path.endsWith(".jpg")) {
                infoWrapper.setImgFileName(path.replace(IMG_EXT, ""));
            } else {
                infoWrapper.setVideoFileName(path.replace(VID_EXT, ""));
            }
        }

        return infoWrapper;
    }

    private static String getPathWithUpDir(String s) {
        Matcher compile = fileSearcherPattern.matcher(s);

        if (compile.find()) {
            return compile.group();
        } else {
            return null;
        }
    }

    @Data
    @Builder
    public static class InfoWrapper {
        private String dirName;

        private String imgFileName;

        private String videoFileName;
    }
}