package com.improve10x.chatwithv2;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.improve10x.chatwithv2.databinding.FragmentTemplatesBinding;

import java.util.ArrayList;


public class TemplatesFragment extends Fragment {

    private FragmentTemplatesBinding templatesBinding;
    private ArrayList<Template> templates;
    private TemplatesAdapter templatesAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        templatesBinding = FragmentTemplatesBinding.inflate(getLayoutInflater());
        setupData();
        setTemplatesRv();
        return templatesBinding.getRoot();
    }

    private void setupData() {
        templates = new ArrayList<>();

        Template message = new Template("Hi, Welcome to Improve 10X");
        templates.add(message);
    }

    private void setTemplatesRv() {
        templatesBinding.templatesRv.setLayoutManager(new LinearLayoutManager(getActivity()));
        templatesAdapter = new TemplatesAdapter();
        templatesAdapter.setTemplates(templates);
        templatesBinding.templatesRv.setAdapter(templatesAdapter);
    }
}