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
