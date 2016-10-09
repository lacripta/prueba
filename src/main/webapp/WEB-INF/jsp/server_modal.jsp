<div class="row">
    <div class="col-lg-12">
        <div class="tabs-container">
            <uib-tabset>
                <uib-tab>
                    <uib-tab-heading>
                        Datos del Servidor
                    </uib-tab-heading>
                    <div class="panel-body">
                        <form name="datosServer">
                            <fieldset>
                                <input-text titulo="ID" valor="selected.id" col="12" required="true" readonly="true"></input-text>
                                <input-text titulo="Nombre" valor="selected.name" col="12" required="true"></input-text>
                                <select-datos titulo="Estado" valor="selected.state" col="12" required="true" datos="estados" label="estado" value="state"></select-datos>
                            </fieldset>
                            <div class="col-xs-12 text-center btn-group">
                                <button class="btn btn-raised btn-info" ng-click="ok(datosServer)">save</button>
                                <button class="btn btn-raised btn-danger" type="submit" ng-click="cancel()"><strong>cancel</strong></button>
                            </div>
                        </form>
                    </div>
                </uib-tab>
            </uib-tabset>
        </div>
    </div>
</div>