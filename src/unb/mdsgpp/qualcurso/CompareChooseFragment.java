package unb.mdsgpp.qualcurso;

import java.util.ArrayList;
import java.util.HashMap;

import helpers.Indicator;
import models.Course;
import models.Evaluation;
import models.GenericBeanDAO;
import models.Institution;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;

public class CompareChooseFragment extends Fragment implements CheckBoxListCallbacks{
	BeanListCallbacks beanCallbacks;
	private static final String COURSE = "course";

	private Spinner yearSpinner = null;
	private AutoCompleteTextView autoCompleteField = null;
	private ListView institutionList = null;
	private Button compareButton = null;
	private CheckBox checkbox = null;
	private ListCompareAdapter compareAdapterList = null;
	
	private int selectedYear;
	private Course selectedCourse;
	private ArrayList<Institution> selectedInstitutions = new ArrayList<Institution>();

	public CompareChooseFragment() {
		super();
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			beanCallbacks = (BeanListCallbacks) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " must implement BeanListCallbacks.");
		}
	}

	@Override
	public void onDetach() {
		super.onDetach();
		beanCallbacks = null;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState){
		View rootView = inflater.inflate(R.layout.compare_choose_fragment, container,
				false);

		if (savedInstanceState != null) {
			if (savedInstanceState.getParcelable(COURSE) != null) {
				setCurrentSelection((Course) savedInstanceState
						.getParcelable(COURSE));

			}
		}

		// bound variables with layout objects
		this.yearSpinner = (Spinner) rootView.findViewById(R.id.compare_year);
		this.autoCompleteField = (AutoCompleteTextView) rootView.findViewById(R.id.autoCompleteTextView);
		this.institutionList = (ListView) rootView.findViewById(R.id.institutionList);
	
		
        this.checkbox = (CheckBox)rootView.findViewById(R.id.compare_institution_checkbox); 

		this.autoCompleteField.setAdapter(new ArrayAdapter<Course>(getActivity().getApplicationContext(), R.layout.custom_textview, Course.getAll()));
		
		// Set objects events
		this.yearSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				if(selectedCourse!=null){
					updateList();
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				
			}
		});

		this.autoCompleteField.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long rowId) {
				setCurrentSelection((Course) parent.getItemAtPosition(position));
				updateList();
			}
		});

		return rootView;
	}

	
	public void setCurrentSelection(Course currentSelection) {
		this.selectedCourse = currentSelection;
	}

	public void updateList() {
		if (yearSpinner.getSelectedItemPosition() != 0) {
			selectedYear = Integer.parseInt(yearSpinner.getSelectedItem()
					.toString());
		} else {
			yearSpinner
					.setSelection(yearSpinner.getAdapter().getCount() - 1);
			selectedYear = Integer.parseInt(yearSpinner.getAdapter()
					.getItem(yearSpinner.getAdapter().getCount() - 1)
					.toString());
		}
		if (this.selectedCourse != null) {
			ArrayList<Institution> courseInstitutions = this.selectedCourse
					.getInstitutions(selectedYear);
			compareAdapterList = new ListCompareAdapter(this.getActivity().getApplicationContext(),
					R.layout.compare_choose_list_item,courseInstitutions, this);

			this.institutionList.setAdapter(compareAdapterList);
		} 
	}

	private void displayToastMessage(String textMenssage) {
		Toast toast = Toast.makeText(
				this.getActivity().getApplicationContext(), textMenssage,
				Toast.LENGTH_SHORT);
		toast.show();
	}
	

	@Override
	public void onCheckedItem(CheckBox checkBox) {
		// TODO Auto-generated method stub
		Institution institution = ((Institution)checkBox.getTag(ListCompareAdapter.INSTITUTION));
		if(!selectedInstitutions.contains(institution)){
			selectedInstitutions.add(institution);
			if(selectedInstitutions.size() == 2){
				System.out.println(Integer.toString(selectedYear));
				Evaluation evaluationA = Evaluation.getFromRelation(selectedInstitutions.get(0).getId(), selectedCourse.getId(), selectedYear);
				Evaluation evaluationB = Evaluation.getFromRelation(selectedInstitutions.get(1).getId(), selectedCourse.getId(), selectedYear);
				beanCallbacks.onBeanListItemSelected(CompareShowFragment.newInstance(evaluationA.getId(), evaluationB.getId()));
			}
			System.out.println(selectedInstitutions.toString());
		}
	}
	@Override
	public void onPause() {
		selectedInstitutions = new ArrayList<Institution>();
		super.onPause();
	}

	@Override
	public void onUnchekedItem(CheckBox checkBox) {
		// TODO Auto-generated method stub
		Institution institution = ((Institution)checkBox.getTag(ListCompareAdapter.INSTITUTION));
		if(selectedInstitutions.contains(institution)){
			selectedInstitutions.remove(institution);
			System.out.println(selectedInstitutions.toString());
		}
	}
}
