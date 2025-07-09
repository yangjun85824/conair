package com.yyld.conair.ds.sap.jco;

import com.sap.conn.jco.*;

/**
 * Sap Function
 * @author      PARK Yong Seo
 * @since       0.1
 */
public class SapFunction {

    final JCoDestination destination;
    final JCoFunction function;

    /**
     * @param jCoFunction
     * @see SapFunctionTemplate
     */
    SapFunction(JCoDestination destination, JCoFunction jCoFunction) {
        this.destination = destination;
        this.function = jCoFunction;
    }

    public JCoFunction getFunction(){
        return this.function;
    }

    /**
     * insert import parameters
     * @return
     */
    public JCoParameterList getImportParameterList() {
        return function.getImportParameterList();
    }

    /**
     * get table that will imported
     * @param tableName
     * @return
     */
    public JCoTable getImportTableParameter(String tableName) {
        return function.getTableParameterList().getTable(tableName);
    }

    public JCoParameterList getTableParameterList(){
        return function.getTableParameterList();
    }
    /**
     * execute and return SapFunctionResult
     * @return
     * @throws JCoException
     */
    public SapFunctionResult execute() throws JCoException {
        function.execute(destination);
        return new SapFunctionResult(function);
    }
}
