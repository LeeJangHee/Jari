package com.example.jari.fragmentback;

import androidx.fragment.app.Fragment;

import java.util.Stack;

public class Fragment_back {
    public Stack<Fragment> fragmentStack;

    public Fragment_back(Stack<Fragment> fragmentStack) {
        this.fragmentStack = fragmentStack;
    }
    public Fragment_back(){
        fragmentStack = new Stack<>();
    }

    public void pushFragmentStack(Fragment frg) {
        fragmentStack.push(frg);
    }

    public Fragment popFragmentStack() {
        return fragmentStack.pop();
    }

    public boolean emptyFragmentStack() {
        if(fragmentStack.empty())  return true;
        else return false;
    }
}
