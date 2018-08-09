package com.scorematics.android.base;

import android.widget.TextView;

public class BaseViewHolderClass {

	public interface ViewHolderClassListener {
		public Object OnviewHolderSelected(Object selectedViewHolderClassObj);

	}

	public enum ClassType {
		COUNTRYLIST,
	}

	public Object getwidgetReferences(ClassType classType,
                                      ViewHolderClassListener viewHolderClassListener) {

		switch (classType) {
		case COUNTRYLIST:
			return viewHolderClassListener
					.OnviewHolderSelected(new CountryListViewHolderClass());


		default:
			break;
		}
		return viewHolderClassListener;

	}

	public class CountryListViewHolderClass {
		public TextView countryName;

	}


}
