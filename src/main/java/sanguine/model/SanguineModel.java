package sanguine.model;

/**
 * extends SanguineModifyModel and ModelReadOnlyInterface because we need to have one interface
 * for the model that extends the modifiable and read only interfaces.
 *
 * @param <C> type SanguineCard
 */
public interface SanguineModel<C extends SanguineCard> extends
    SanguineModifyModel<C>, ModelReadOnlyInterface, ModelControllerPublisher{
}
