package org.dualword.android.notedemo;

import android.support.test.filters.RequiresDevice;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * Created by alex on 1/10/17.
 */

@RequiresDevice
@RunWith(Suite.class)
@SuiteClasses({ApplicationTest.class, EspressoTest.class})
public class MainSuit {}