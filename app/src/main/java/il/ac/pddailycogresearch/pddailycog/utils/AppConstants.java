/*
 * Copyright (C) 2017 MINDORKS NEXTGEN PRIVATE LIMITED
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://mindorks.com/license/apache-v2
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License
 */

package il.ac.pddailycogresearch.pddailycog.utils;

/**
 * Created by amitshekhar on 08/01/17.
 */

public final class AppConstants {

    public static final String STATUS_CODE_SUCCESS = "success";
    public static final String STATUS_CODE_FAILED = "failed";
    public static final String PACKAGE_NAME = "il.ac.pddailycogresearch.pddailycog";


    public static final int API_STATUS_CODE_LOCAL_ERROR = 0;

    public static final String CHORES_KEY = "chores";
    public static final String USERS_KEY = "users";
    public static final String  MAIL_SUFFIX = "@pddaily.com";

    public static final long CHORES_AMOUNT = 1;

    public static final long NULL_INDEX = -1L;

    public static final String SEED_DATABASE_OPTIONS = "seed/options.json";
    public static final String SEED_DATABASE_QUESTIONS = "seed/questions.json";

    public static final String TIMESTAMP_FORMAT = "dd-MM-yyyy_HH-mm-ss";
    public static final String HAS_NO_CHORES_MSG = "User has no chores";//TODO wtf
    public static final String LOCAL_URI_PREFIX = "content";


    private AppConstants() {
        // This utility class is not publicly instantiable
    }
}
