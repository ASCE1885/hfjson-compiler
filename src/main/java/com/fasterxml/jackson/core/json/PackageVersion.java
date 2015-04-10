package com.fasterxml.jackson.core.json;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.core.Versioned;
import com.fasterxml.jackson.core.util.VersionUtil;

/**
 * Created by guhaoxin on 15/4/8.
 */
public class PackageVersion implements Versioned {

    public static final Version VERSION = VersionUtil.parseVersion("2.5.0", "com.fasterxml.jackson.core", "jackson-core");

    @Override
    public Version version() {
        return VERSION;
    }
}
