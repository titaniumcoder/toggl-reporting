import React from 'react';
import { Button, ButtonGroup, Col, Row } from 'reactstrap';
import moment, { Moment } from 'moment';
import { AvField, AvForm } from 'availity-reactstrap-validation';

interface IHeaderProps {
    dateFrom: Moment;
    dateTo: Moment;
    enabled: boolean;

    loadFromTo: (from: Moment, to: Moment) => void;
    createExcel: () => void;
    setBilled: () => void;
    setUnbilled: () => void;

    logout: () => void;
}

const Header: React.FC<IHeaderProps> = ({ enabled, dateFrom, dateTo, loadFromTo, createExcel, setBilled, setUnbilled, logout }) => {
    const defaultFromTo = {
        dateFrom: dateFrom.format('YYYY-MM-DD'),
        dateTo: dateTo.format('YYYY-MM-DD')
    };

    const handleSubmit = (event, values) => {
        loadFromTo(moment(values.dateFrom, 'YYYY-MM-DD'), moment(values.dateTo, 'YYYY-MM-DD'));
    };

    return (
        <Row style={{ paddingTop: "0.5rem" }}>
            <Col>
                <AvForm onValidSubmit={handleSubmit} model={defaultFromTo}>
                    <Row>
                        <Col xs="4">
                            <AvField name="dateFrom" type="date" required/>
                        </Col>
                        <Col xs="4">
                            <AvField name="dateTo" type="date" required/>
                        </Col>
                        <Col xs="auto">
                            <Button type="submit" color="primary">Laden</Button>
                        </Col>
                    </Row>
                </AvForm>
            </Col>
            <Col xs="auto">
                <ButtonGroup>
                    <Button disabled={!enabled} color="secondary" onClick={createExcel}>Excel</Button>
                    <Button disabled={!enabled} color="danger" onClick={setBilled}>Abgerechnet</Button>
                    <Button disabled={!enabled} color="danger" onClick={setUnbilled}>Zurücksetzen</Button>
                </ButtonGroup>
            </Col>
            <Col xs="auto" style={{ paddingLeft: "0.4rem" }}>
                <Button color="info" onClick={logout}>Ausloggen</Button>
            </Col>
        </Row>
    );
};

export default Header;
