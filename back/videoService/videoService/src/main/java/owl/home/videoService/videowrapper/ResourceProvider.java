package owl.home.videoService.videowrapper;


import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.reactive.function.server.ServerRequest;

import java.io.File;
import java.util.Optional;
import java.util.regex.Pattern;


public class ResourceProvider {
    private static final String VIDEO_EXT = ".mp4";

    private static final String IMAGE_EXT = ".jpg";

    private static final String MAIN_DIR = "video";

    private ResourceProvider() {
    }

    public static Resource requestToResource(ServerRequest request, ResourceType resourceType) {
        Resource resource = new ClassPathResource(getResourceName(request, resourceType));

        assert resource.exists();

        return resource;
    }

    private static String getResourceName(ServerRequest request, ResourceType resourceType) {
        return Optional.of(request.pathVariable("videoDir"))
                .filter(dir -> !dir.isBlank())
                .map(dir -> toFilePath(dir, request, resourceType))
                .filter(ResourceProvider::isMatch)
                .orElseThrow();
    }

    private static boolean isMatch(String pathToFile) {
        return Pattern
                .compile("video/\\d{1,2}/.+\\.(jpg|mp4)")
                .matcher(pathToFile)
                .matches();
    }

    private static String toFilePath(String dir, ServerRequest request, ResourceType resourceType) {
        return MAIN_DIR +
                "/" +
                dir +
                "/" +
                request.pathVariable("videoName") +
                getExtension(resourceType);
    }

    private static String getExtension(ResourceType resourceType) {
        return resourceType.equals(ResourceType.VIDEO) ? VIDEO_EXT : IMAGE_EXT;
    }
}