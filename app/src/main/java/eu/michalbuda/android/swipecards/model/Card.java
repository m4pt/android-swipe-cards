/*
 * Copyright 2017, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package eu.michalbuda.android.swipecards.model;

public interface Card {
    int getId();
    String getName();
    int getGuessed(); // ..., -2 - not guessed twice, -1 - not guessed once, 0 - no views by user, 1 - guessed once, 2 - guessed twice, ...
    int getCategory();
}
