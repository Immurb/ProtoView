/* 
 * Copyright 2016 Deutsche Bundesbank
 * Licensed under the EUPL, Version 1.1 or - as soon they will be approved
 * by the European Commission - subsequent versions of the EUPL (the "Licence");
 * You may not use this work except in compliance with the Licence.
 * You may obtain a copy of the Licence at:
 * 
 * http://ec.europa.eu/idabc/eupl
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the Licence is distributed on an "AS IS" basis,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the Licence for the specific language governing permissions and
 * limitations under the Licence.
 */
package de.bundesbank.jdemetra.protoview;

import ec.tss.sa.documents.X13Document;
import ec.tstoolkit.utilities.DefaultInformationExtractor;
import ec.tstoolkit.utilities.Id;
import ec.tstoolkit.utilities.LinearId;
import ec.ui.view.tsprocessing.ComposedProcDocumentItemFactory;
import ec.ui.view.tsprocessing.PooledItemUI;
import ec.ui.view.tsprocessing.ProcDocumentItemFactory;
import ec.ui.view.tsprocessing.ProcDocumentViewFactory;
import org.openide.util.lookup.ServiceProvider;

/**
 *
 * @author Thomas Witthohn
 */
@ServiceProvider(service = ProcDocumentItemFactory.class, position = 220020)
public class ProtoFactory extends ComposedProcDocumentItemFactory<X13Document, X13Document> {

    public static final String PROTO = "Compare";
    public static final Id PROTO_MAIN = new LinearId(PROTO);

    public ProtoFactory() {
        super(X13Document.class, PROTO_MAIN, new DefaultInformationExtractor<X13Document, X13Document>() {
            @Override
            public X13Document retrieve(X13Document source) {
                return source;
            }
        }, new PooledItemUI<ProcDocumentViewFactory<X13Document>.View, X13Document, ProtoView>(ProtoView.class) {
            @Override
            protected void init(ProtoView c, ProcDocumentViewFactory.View host, X13Document information) {
                c.set(information);
            }
        });
    }
}
