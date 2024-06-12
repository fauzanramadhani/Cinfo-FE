package com.ndc.core.data.constant

object Event {
    // POST GLOBAL
    const val POST_GLOBAL = "postGlobal"
    const val CREATE_POST_GLOBAL = "createPostGlobal"
    const val EDIT_POST_GLOBAL = "editPostGlobal"
    const val DELETE_POST_GLOBAL = "deletePostGlobal"
    const val ON_DELETE_POST_GLOBAL = "onDeletedPostGlobal"
    // PRIVATE POST
    const val POST_PRIVATE = "-post"
    const val PRIVATE_POST_PRIVATE = "createPost"
    const val EDIT_POST_PRIVATE = "editPost"
    const val DELETE_POST_PRIVATE = "deletePost"
    const val ON_DELETE_POST_PRIVATE = "-on-delete-post"
    // ROOM
    const val ROOM = "room"
    const val CREATE_ROOM = "createRoom"
    const val DELETE_ROOM = "deleteRoom"
    const val EDIT_ROOM = "editRoom"
    const val ON_DELETE_ROOM = "onDeleteRoom"
    // MEMBER
    const val MEMBER = "-member"
    const val ADD_MEMBER = "addMember"
    const val DELETE_MEMBER = "deleteMember"
    const val ON_ROOM_UPDATE = "-on-room-update"
    const val ON_DELETE_MEMBER = "-on-delete-member"
}