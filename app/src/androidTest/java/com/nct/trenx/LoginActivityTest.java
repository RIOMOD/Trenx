package com.nct.trenx;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isEnabled;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.not;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.nct.trenx.activity.LoginActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class LoginActivityTest {

    @Rule
    public ActivityScenarioRule<LoginActivity> activityRule =
            new ActivityScenarioRule<>(LoginActivity.class);

    @Test
    public void testLoginButtonDisabledInitially() {
        // Nút Sign In mặc định phải bị disable
        onView(withId(R.id.btn_sign_in)).check(matches(not(isEnabled())));
    }

    @Test
    public void testLoginButtonDisabledWhenPasswordTooShort() {
        // Nhập email hợp lệ
        onView(withId(R.id.et_email)).perform(typeText("test@example.com"), closeSoftKeyboard());
        
        // Nhập mật khẩu ngắn (< 6 ký tự)
        onView(withId(R.id.et_password)).perform(typeText("12345"), closeSoftKeyboard());
        
        // Nút Sign In vẫn phải bị disable
        onView(withId(R.id.btn_sign_in)).check(matches(not(isEnabled())));
    }

    @Test
    public void testLoginButtonEnabledWhenCredentialsValid() {
        // Nhập email hợp lệ
        onView(withId(R.id.et_email)).perform(typeText("test@example.com"), closeSoftKeyboard());
        
        // Nhập mật khẩu hợp lệ (>= 6 ký tự)
        onView(withId(R.id.et_password)).perform(typeText("123456"), closeSoftKeyboard());
        
        // Nút Sign In phải được enable
        onView(withId(R.id.btn_sign_in)).check(matches(isEnabled()));
    }
}
