/*
 * Copyright (c) 2017 Gowtham Parimelazhagan.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.gm.rtonumbermatcher.ui.generate;

import android.app.Application;

import com.gm.archmvvm.mvvm.BaseModel;
import com.gm.rtonumbermatcher.ui.generate.model.TextContent;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

import static com.gm.rtonumbermatcher.util.Utils.digSum;
import static com.gm.rtonumbermatcher.util.Utils.findDuplicateChars;

/**
 * Author     : Gowtham
 * Email      : goutham.gm11@gmail.com
 * Github     : https://github.com/goutham106
 * Created on : 9/26/17.
 */

public class RtoModel extends BaseModel {

    @Inject
    public RtoModel(Application application) {
        super(application);
    }

    Observable<List<TextContent>> getDatas(int start, int end, int search) {
        List<TextContent> textContent = new ArrayList<>();
        for (int i = start; i <= end; i++) {
            if (search == -1) {
                textContent.add(new TextContent("" + i));
            } else if (digSum(i) == search) {
                textContent.add(new TextContent("" + i));
            }
        }
        return Observable.just(textContent);
    }

    Observable<List<TextContent>> getSortedData(int start, int end, int search, int checkByValue) {
        List<String> mData = new ArrayList<>();
        List<TextContent> textContent = new ArrayList<>();
        for (int i = start; i <= end; i++) {
            if (search == -1) {
                mData.add("" + i);
            } else if (digSum(i) == search) {
                mData.add("" + i);
            }
        }
        if (mData.size() > 0) {
            for (String list : mData) {
                if (findDuplicateChars(list, checkByValue)) {
                    textContent.add(new TextContent("" + list));
                }
            }
        }
        return Observable.just(textContent);
    }

}
