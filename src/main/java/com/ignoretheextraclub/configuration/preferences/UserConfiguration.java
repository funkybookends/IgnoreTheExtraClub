package com.ignoretheextraclub.configuration.preferences;

import com.ignoretheextraclub.model.user.PatternList;
import com.ignoretheextraclub.model.user.Permission;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by caspar on 11/03/17.
 */
public class UserConfiguration
{
    public static final String FAVOURITES = "Favourites";

    public static final List<Permission> DEFAULT_PERMISSIONS = Arrays.asList(
            Permission.COMMENTER_WRITER,
            Permission.USER
    );

    private static final String LEARNING = "Learning";

    public static Map<String, PatternList> defaultUserPatternList()
    {
        final HashMap<String, PatternList> map = new HashMap<>();

        map.put(FAVOURITES, new PatternList(FAVOURITES, false, new ArrayList<>(), Instant.now(), Instant.now()));
        map.put(LEARNING, new PatternList(FAVOURITES, false, new ArrayList<>(), Instant.now(), Instant.now()));

        return map;
    }
}
