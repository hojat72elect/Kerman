package com.kerman.core.scenes.scene2d.actions

/**
 * Info : This interface was inspired by "com.badlogic.gdx.scenes.scene2d.actions.FinishableAction".
 *
 * An interface for actions that can be finished manually.
 */
interface FinishableAction {

    /**
     * Manually finishes the action, performing necessary finalization steps.
     */
    fun finish()
}
