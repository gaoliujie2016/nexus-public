/*
 * Sonatype Nexus (TM) Open Source Version
 * Copyright (c) 2008-present Sonatype, Inc.
 * All rights reserved. Includes the third-party code listed at http://links.sonatype.com/products/nexus/oss/attributions.
 *
 * This program and the accompanying materials are made available under the terms of the Eclipse Public License Version 1.0,
 * which accompanies this distribution and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Sonatype Nexus (TM) Professional Version is available from Sonatype, Inc. "Sonatype" and "Sonatype Nexus" are trademarks
 * of Sonatype, Inc. Apache Maven is a trademark of the Apache Software Foundation. M2eclipse is a trademark of the
 * Eclipse Foundation. All other trademarks are the property of their respective owners.
 */
package org.sonatype.nexus.internal.node;

import java.util.Set;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.goodies.lifecycle.LifecycleSupport;
import org.sonatype.nexus.common.node.ClusteredNodeAccess;
import org.sonatype.nexus.common.node.LocalNodeAccess;

import com.google.common.collect.ImmutableSet;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Single {@link ClusteredNodeAccess}.
 *
 * @since 3.1
 */
@Named("single")
@Singleton
public class SingleClusteredNodeAccess
    extends LifecycleSupport
    implements ClusteredNodeAccess
{
  private final LocalNodeAccess localNodeAccess;

  private Set<String> memberIds;

  @Inject
  public SingleClusteredNodeAccess(final LocalNodeAccess localNodeAccess) {
    this.localNodeAccess = checkNotNull(localNodeAccess);
  }

  @Override
  protected void doStart() throws Exception {
    memberIds = ImmutableSet.of(localNodeAccess.getId());
  }

  @Override
  public Set<String> getMemberIds() {
    ensureStarted();
    return memberIds;
  }
}
