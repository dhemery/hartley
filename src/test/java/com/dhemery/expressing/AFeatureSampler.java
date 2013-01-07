package com.dhemery.expressing;

import com.dhemery.core.Feature;
import com.dhemery.core.Sampler;
import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Rule;
import org.junit.Test;

import static com.dhemery.expressing.FeatureSampler.sampled;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class AFeatureSampler {
    @Rule public JUnitRuleMockery context = new JUnitRuleMockery();

    @Test
    public void samplesByQueryingTheFeatureOfTheSubject() {
        final String subject = "foo";
        final Feature<String,String> feature = context.mock(Feature.class);
        Sampler<String> featureSampler = sampled(subject, feature);

        context.checking(new Expectations() {{
            oneOf(feature).of(with(sameInstance(subject)));
        }});

        featureSampler.takeSample();
    }

    @Test
    public void yieldsTheFeatureOfTheSubject(){
        final String subject = "foo";
        final Feature<String,String> feature = context.mock(Feature.class);
        final String featureValue = "bunko";
        Sampler<String> featureSampler = sampled(subject, feature);

        context.checking(new Expectations(){{
            allowing(feature).of(with(sameInstance(subject)));
            will(returnValue(featureValue));
        }});
        featureSampler.takeSample();

        assertThat(featureSampler.sampledValue(), is(featureValue));
    }

    @Test
    public void queriesTheFeatureOnlyToTakeASample(){
        final String subject = "foo";
        final Feature<String,String> feature = context.mock(Feature.class);
        Sampler<String> featureSampler = sampled(subject, feature);

        context.checking(new Expectations() {{
            allowing(feature).of(with(any(String.class)));
        }});
        featureSampler.takeSample();

        context.checking(new Expectations(){{
            never(feature).of(with(any(String.class)));
        }});
        featureSampler.sampledValue();
        featureSampler.sampledValue();
        featureSampler.sampledValue();
        featureSampler.sampledValue();
        featureSampler.sampledValue();
    }

    @Test
    public void describesItselfAsSampledFeatureOfSubject() {
        Subject subject = context.mock(Subject.class, "montecristo");
        Feature<Subject,String> feature = context.mock(Feature.class, "count");
        String expectedDescription = "sampled count of montecristo";
        assertThat(sampled(subject, feature).toString(), is(expectedDescription));
    }

    @Test
    public void describesItsSubjectDiagnostically() {
        assertThat(sampled(null,       feature()).toString(), endsWith(Diagnostic.descriptionOf(null)));
        assertThat(sampled('q',        feature()).toString(), endsWith(Diagnostic.descriptionOf('q')));
        assertThat(sampled("foo",      feature()).toString(), endsWith(Diagnostic.descriptionOf("foo")));
        assertThat(sampled((byte) 22,  feature()).toString(), endsWith(Diagnostic.descriptionOf((byte)22)));
        assertThat(sampled((short) 99, feature()).toString(), endsWith(Diagnostic.descriptionOf((short)99)));
        assertThat(sampled(3,          feature()).toString(), endsWith(Diagnostic.descriptionOf(3)));
        assertThat(sampled(12L,        feature()).toString(), endsWith(Diagnostic.descriptionOf(12L)));
        assertThat(sampled(4.2f,       feature()).toString(), endsWith(Diagnostic.descriptionOf(4.2f)));
        assertThat(sampled(6.1d,       feature()).toString(), endsWith(Diagnostic.descriptionOf(6.1d)));
    }

    private <T,Object> Feature<T, Object> feature() {
        return new Feature<T,Object>(){
            @Override public Object of(T subject) { return null; }
        };
    }

    public interface Subject {}
}
