package com.dhemery.polling.fixtures;

import com.dhemery.core.Action;
import com.dhemery.core.Condition;
import com.dhemery.core.Feature;
import com.dhemery.core.NamedAction;
import org.hamcrest.Description;

public class Polling {
    public static class Actions {
        public static Action<Condition> doNothing() {
            return new DoNothing();
        }

        public static Action<Condition> log(String name) {
            return new Log(name);
        }

        public static class Log extends NamedAction<Condition> {
            public Log(String name) {
                super(name);
            }

            @Override
            public void actOn(Condition subject) {

            }

            @Override
            public void describeTo(Description description) {

            }
        }


        public static class DoNothing implements Action<Condition> {
            @Override  public void actOn(Condition subject) {}

            @Override  public void describeTo(Description description) {}
        }

    }

    public static class Conditions {
        public static SatisfiedOnPollNumber satisfiedOnPollNumber(int satisfactionPollNumber) {
            return new SatisfiedOnPollNumber(satisfactionPollNumber);
        }

        public static class SatisfiedOnPollNumber implements Condition {
            private final int satisfactionPollNumber;
            public int pollCount;

            public SatisfiedOnPollNumber(int satisfactionPollNumber) {
                this.satisfactionPollNumber = satisfactionPollNumber;
            }

            @Override
            public boolean isSatisfied() {
                return satisfactionPollNumber >= ++pollCount;
            }

            @Override
            public void describeTo(Description description) {
            }

            @Override
            public void describeDissatisfactionTo(Description description) {
            }
        }

    }

    public static class Timeframes {
        public static Feature<Condition, Boolean> forever() {
            return new Forever();
        }

        public static class Forever implements Feature<Condition, Boolean> {
            @Override  public Boolean of(Condition subject) { return true; }

            @Override public void describeTo(Description description) {}
        }
    }
}
