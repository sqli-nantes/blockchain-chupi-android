package com.example.joel.automotive.contract;

import ethereumjava.solidity.ContractType;
import ethereumjava.solidity.SolidityEvent;
import ethereumjava.solidity.SolidityFunction;
import ethereumjava.solidity.types.SUInt;
import ethereumjava.solidity.types.SVoid;

/**
 * Created by root on 25/10/16.
 */

public interface ChoupetteContract extends ContractType {

    @SolidityEvent.Anonymous(false)
    @SolidityEvent.Parameters({
            @SolidityEvent.Parameter(indexed = false, name = "state", type = SUInt.SUInt256.class)
    })
    SolidityEvent<SUInt.SUInt256> OnStateChanged();

    @SolidityFunction.ReturnType(SVoid.class)
    SolidityFunction RentMe();

    @SolidityFunction.ReturnType(SVoid.class)
    SolidityFunction StopRent();

    @SolidityFunction.ReturnType(SVoid.class)
    SolidityFunction StartRent();

    @SolidityFunction.ReturnType(SVoid.class)
    SolidityFunction ValidateTravel();

    @SolidityFunction.ReturnType(SVoid.class)
    SolidityFunction GoTo(SUInt.SUInt256 x, SUInt.SUInt256 y);
    @SolidityFunction.ReturnType(SUInt.SUInt256.class)
    SolidityFunction GetPrice();

}
