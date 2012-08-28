package com.dhemery.network;

import com.dhemery.serializing.Codec;
import org.jmock.Expectations;
import org.jmock.auto.Mock;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.sameInstance;

public class ACodecEndpoint {
    interface Ignored{}
    interface TheUserReturnType{}
    public static final String IGNORED_PATH = "ignored";

    @Rule public JUnitRuleMockery context = new JUnitRuleMockery();
    @Mock public Codec codec;
    @Mock public Endpoint delegateEndpoint;
    private CodecEndpoint endpoint;

    @Before
    public void setup() {
        endpoint = new CodecEndpoint(delegateEndpoint, codec);
    }

    @Test
    public void getCallsDelegateWithPath() {
        final String path = "/thepath";
        context.checking(new Expectations(){{
            oneOf(delegateEndpoint).get(path);
            ignoring(codec);
        }});

        endpoint.get(path, Ignored.class);
    }

    @Test
    public void asksCodecToDecodeDelegateResponseIntoRequestedReturnType() {
        final String theGetResponse = "the get response";
        context.checking(new Expectations(){{
            allowing(delegateEndpoint).get(with(any(String.class))); will(returnValue(theGetResponse));
            oneOf(codec).decode(with(theGetResponse), with(TheUserReturnType.class));
        }});

        endpoint.get(IGNORED_PATH, TheUserReturnType.class);
    }

    @Test
    public void returnsCodecResponse() {
        final TheUserReturnType theCodecResponse = new TheUserReturnType(){};
        context.checking(new Expectations(){{
            ignoring(delegateEndpoint);
            oneOf(codec).decode(with(any(String.class)), with(any(Class.class)));
                will(returnValue(theCodecResponse));

        }});

        assertThat(endpoint.get(IGNORED_PATH, TheUserReturnType.class), is(sameInstance(theCodecResponse)));
    }
}
