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

import ec.nbdemetra.ui.NbComponents;
import ec.satoolkit.algorithm.implementation.X13ProcessingFactory;
import ec.satoolkit.x13.X13Specification;
import ec.tss.Ts;
import ec.tss.TsFactory;
import ec.tss.sa.documents.X13Document;
import ec.tstoolkit.algorithm.CompositeResults;
import ec.tstoolkit.modelling.ModellingDictionary;
import ec.tstoolkit.timeseries.simplets.TsData;
import ec.ui.chart.JTsChart;
import ec.ui.grid.JTsGrid;
import ec.ui.interfaces.IDisposable;
import java.awt.BorderLayout;
import javax.swing.JComponent;
import javax.swing.JSplitPane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Thomas Witthohn
 */
public class ProtoView extends JComponent implements IDisposable {

    private static final Logger log = LoggerFactory.getLogger(ProtoView.class);

    X13Specification old;
    private JTsChart chart;
    private JTsGrid grid;

    public ProtoView() {
        //Define your View
        setLayout(new BorderLayout());
        chart = new JTsChart();
        grid = new JTsGrid();

        JSplitPane pane = NbComponents.newJSplitPane(JSplitPane.VERTICAL_SPLIT, chart, grid);
        pane.setOneTouchExpandable(true);
        pane.setDividerLocation(.5);
        pane.setResizeWeight(.5);

        add(pane, BorderLayout.CENTER);
    }

    public void set(X13Document doc) {

        if (doc == null) {
            log.info("Null-Document");
            return;
        }
        if (old == null) {
            old = doc.getSpecification();
        }
//Write Custom Code here
        chart.getTsCollection().clear();
        grid.getTsCollection().clear();
        Ts input = doc.getInput();

        CompositeResults oldResult = X13ProcessingFactory.process(input.getTsData(), old);
        TsData oldSaData = oldResult.getData(ModellingDictionary.SA, TsData.class);
        Ts saOld = TsFactory.instance.createTs("SA by Old Spec", null, oldSaData);
        chart.getTsCollection().add(saOld);
        grid.getTsCollection().add(saOld);

        TsData saData = doc.getResults().getData(ModellingDictionary.SA, TsData.class);
        Ts sa = TsFactory.instance.createTs("SA by us", null, saData);
        chart.getTsCollection().add(sa);
        grid.getTsCollection().add(sa);

//        X13Specification[] allSpecs = X13Specification.allSpecifications();
//
//        for (X13Specification specification : allSpecs) {
//            CompositeResults result = X13ProcessingFactory.process(input.getTsData(), specification);
//            TsData saSpecData = result.getData(ModellingDictionary.SA, TsData.class);
//            Ts saSpec = TsFactory.instance.createTs("SA by " + specification.toString(), null, saSpecData);
//            chart.getTsCollection().add(saSpec);
//            grid.getTsCollection().add(saSpec);
//        }
    }

    @Override
    public void dispose() {
        old = null;
        //Dispose of other variables
    }

}
