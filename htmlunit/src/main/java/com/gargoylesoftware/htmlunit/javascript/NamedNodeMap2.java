/*
 * Copyright (c) 2002-2016 Gargoyle Software Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.gargoylesoftware.htmlunit.javascript;

import static com.gargoylesoftware.js.nashorn.internal.objects.annotations.BrowserFamily.CHROME;
import static com.gargoylesoftware.js.nashorn.internal.objects.annotations.BrowserFamily.FF;
import static com.gargoylesoftware.js.nashorn.internal.objects.annotations.BrowserFamily.IE;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.DomNode;
import com.gargoylesoftware.js.nashorn.ScriptUtils;
import com.gargoylesoftware.js.nashorn.SimpleObjectConstructor;
import com.gargoylesoftware.js.nashorn.SimplePrototypeObject;
import com.gargoylesoftware.js.nashorn.internal.objects.Global;
import com.gargoylesoftware.js.nashorn.internal.objects.annotations.ClassConstructor;
import com.gargoylesoftware.js.nashorn.internal.objects.annotations.ScriptClass;
import com.gargoylesoftware.js.nashorn.internal.objects.annotations.WebBrowser;
import com.gargoylesoftware.js.nashorn.internal.runtime.PrototypeObject;
import com.gargoylesoftware.js.nashorn.internal.runtime.ScriptFunction;

@ScriptClass
public class NamedNodeMap2 extends SimpleScriptObject {

    private final org.w3c.dom.NamedNodeMap attributes_;

    /**
     * Default Constructor
     */
    private NamedNodeMap2() {
        attributes_ = null;
    }

    /**
     * Creates a new named node map for the specified element.
     *
     * @param element the owning element
     */
    public NamedNodeMap2(final DomElement element) {
        final Global global = NashornJavaScriptEngine.getGlobal(element.getPage().getEnclosingWindow());
        setProto(global.getPrototype(getClass()));
        ScriptUtils.initialize(this);

        attributes_ = element.getAttributes();
        setDomNode(element, false);
    }

    public static NamedNodeMap2 constructor(final boolean newObj, final Object self) {
        final NamedNodeMap2 host = new NamedNodeMap2();
        host.setProto(((Global) self).getPrototype(host.getClass()));
        ScriptUtils.initialize(host);
        return host;
    }

    /**
     * <span style="color:red">INTERNAL API - SUBJECT TO CHANGE AT ANY TIME - USE AT YOUR OWN RISK.</span><br>
     *
     * Gets the specified attribute but does not handle the synthetic class attribute for IE.
     * @see #getNamedItem(String)
     *
     * @param name attribute name
     * @return the attribute node, {@code null} if the attribute is not defined
     */
    public Object getNamedItemWithoutSytheticClassAttr(final String name) {
        if (attributes_ != null) {
            final DomNode attr = (DomNode) attributes_.getNamedItem(name);
            if (attr != null) {
                return attr.getScriptableObject();
            }
        }

        return null;
    }

    private static MethodHandle staticHandle(final String name, final Class<?> rtype, final Class<?>... ptypes) {
        try {
            return MethodHandles.lookup().findStatic(NamedNodeMap2.class,
                    name, MethodType.methodType(rtype, ptypes));
        }
        catch (final ReflectiveOperationException e) {
            throw new IllegalStateException(e);
        }
    }

    @ClassConstructor({@WebBrowser(CHROME), @WebBrowser(FF)})
    public static final class FunctionConstructor extends ScriptFunction {
        public FunctionConstructor() {
            super("NamedNodeMap", 
                    staticHandle("constructor", NamedNodeMap2.class, boolean.class, Object.class),
                    null);
            final Prototype prototype = new Prototype();
            PrototypeObject.setConstructor(prototype, this);
            setPrototype(prototype);
        }
    }

    public static final class Prototype extends SimplePrototypeObject {
        Prototype() {
            super("NamedNodeMap");
        }
    }

    @ClassConstructor(@WebBrowser(IE))
    public static final class ObjectConstructor extends SimpleObjectConstructor {
        public ObjectConstructor() {
            super("NamedNodeMap");
        }
    }
}