package com.julizey.moreplugins.updater;

import java.util.Collections;
import java.util.List;

import org.jetbrains.annotations.ApiStatus;

public class PlatformData {
    public static abstract class AbstractPlatformData {
        private final String name;

        public AbstractPlatformData(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

    }

    public static class GithubData extends AbstractPlatformData {
        private final String githubRepo;

        public GithubData(String githubRepo) {
            super("github");
            this.githubRepo = githubRepo;
        }

        public String getGithubRepo() {
            return githubRepo;
        }
    }

    public static class HangarData extends AbstractPlatformData {
        private final String hangarProjectSlug;

        public HangarData(String hangarProjectSlug) {
            super("hangar");
            this.hangarProjectSlug = hangarProjectSlug;
        }

        public String getHangarProjectSlug() {
            return hangarProjectSlug;
        }
    }

    public static class SpigotData extends AbstractPlatformData {
        private final String spigotResourceId;

        public SpigotData(String spigotResourceId) {
            super("spigot");
            this.spigotResourceId = spigotResourceId;
        }

        public String getSpigotResourceId() {
            return spigotResourceId;
        }
    }

    public static class ModrinthData extends AbstractPlatformData {
        private final String modrinthProjectId;
        private final List<String> versionTypes;
        private final boolean featuredOnly;

        public ModrinthData(String modrinthProjectId, List<String> versionTypes, boolean featuredOnly) {
            super("modrinth");
            this.modrinthProjectId = modrinthProjectId;
            this.versionTypes = versionTypes;
            this.featuredOnly = featuredOnly;
        }

        public ModrinthData(String modrinthProjectId, String versionType, boolean featuredOnly) {
            this(modrinthProjectId, Collections.singletonList(versionType), featuredOnly);
        }

        public ModrinthData(String modrinthProjectId, boolean featuredOnly) {
            this(modrinthProjectId, VersionType.ALL, featuredOnly);
        }

        public String getModrinthProjectId() {
            return modrinthProjectId;
        }

        public boolean specifiesVersionType() {
            return this.versionTypes != null;
        }

        @ApiStatus.Internal
        public String getVersionType() {
            return this.versionTypes != null ? this.versionTypes.get(0) : null;
        }

        public List<String> getVersionTypes() {
            return versionTypes;
        }

        public boolean includeFeaturedOnly() {
            return featuredOnly;
        }

        public static class VersionType {
            public static final List<String> ALL = null;
            public static final String RELEASE = "release";
            public static final String BETA = "beta";
            public static final String ALPHA = "alpha";
        }
    }
}
